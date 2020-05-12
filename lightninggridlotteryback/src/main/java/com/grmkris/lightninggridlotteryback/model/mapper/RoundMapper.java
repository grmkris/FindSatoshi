package com.grmkris.lightninggridlotteryback.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoundMapper {

    RoundMapper INSTANCE = Mappers.getMapper( RoundMapper.class );
}