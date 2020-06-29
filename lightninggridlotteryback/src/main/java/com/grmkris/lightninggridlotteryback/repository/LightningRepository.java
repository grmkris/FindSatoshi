package com.grmkris.lightninggridlotteryback.repository;

import java.io.IOException;

import com.grmkris.lightninggridlotteryback.model.ClaimRequest;
import com.grmkris.lightninggridlotteryback.model.TicketRequest;
import com.grmkris.lightninggridlotteryback.model.TicketResponse;
import com.grmkris.lightninggridlotteryback.model.OpenNode.transfers.WithdrawalRequest;

import org.brunocvcunha.opennode.api.OpenNodeService;
import org.brunocvcunha.opennode.api.OpenNodeServiceFactory;
import org.brunocvcunha.opennode.api.model.OpenNodeCharge;
import org.brunocvcunha.opennode.api.model.OpenNodeCreateCharge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class LightningRepository {

    @Value("${find-satoshi.open-node}")
    String APIKEY;

    @Value("${find-satoshi.url}")
    String CALLBACK;

    @Value("${find-satoshi.success-url}")
    String SUCCESS;
    

    /**
     * Requests new lnurl from openNode and returns ticketResponse object
     * 
     * @param ticketRequest
     * @param ticket_amount
     * @return
     * @throws IOException
     */
    public TicketResponse newTicketOpenNode(TicketRequest ticketRequest, Double ticket_amount) throws IOException {

        OpenNodeService service = OpenNodeServiceFactory.buildClient(APIKEY);

        OpenNodeCreateCharge createCharge = OpenNodeCreateCharge.builder().orderId("internal id")
                .description(ticketRequest.getSecret()).amount(ticket_amount).callbackUrl(this.CALLBACK)
                .customerEmail("kristjan.grm1@gmail.com").customerName("customerName")
                .callbackUrl(this.CALLBACK + "findSatoshi/success").successUrl(this.SUCCESS)
                // .currency(OpenNodeCurrency.EUR) // default is satoshis
                .build();

        OpenNodeCharge createdCharge = service.createCharge(createCharge).execute().body().getData();

        TicketResponse ticketResponse = TicketResponse.builder().amount(ticket_amount).secret(ticketRequest.getSecret())
                .lightningInvoice(createdCharge.getLightningInvoice().getPayreq()).numbers(ticketRequest.getPredict())
                .openNodeID(createdCharge.getId()).settledAt(createdCharge.getLightningInvoice().getSettledAt())
                .status(createdCharge.getStatus().name()).build();

        return ticketResponse;

    }

    /**
     * Processes withdrawal request and returns openNode response
     * 
     * @param claimRequest
     * @return
     */
    public String ticketWithdrawalOpenNode(ClaimRequest claimRequest) {

        WithdrawalRequest withdrawalRequest = WithdrawalRequest.builder().address(claimRequest.getLightningInvoice())
                .amount("200").type("ln").callbackUrl("https://example.com/webhook/opennode/withdrawal").build();

        String OPENNODE_URL = "https://api.opennode.co/v2";
        WebClient webClient = WebClient.builder().filter(logRequest()).baseUrl(OPENNODE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", APIKEY).build();

        Mono<String> result = webClient.post().uri("/withdrawals")
                .body(Mono.just(withdrawalRequest), WithdrawalRequest.class).retrieve().bodyToMono(String.class);

        String result1 = result.block();
        return result1;
    }

    /**
     * This method returns filter function which will log request data
     * @return
     */
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

}