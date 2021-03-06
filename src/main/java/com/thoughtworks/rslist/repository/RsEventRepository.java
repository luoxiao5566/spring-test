package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsEventDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface RsEventRepository extends CrudRepository<RsEventDto, Integer> {
  List<RsEventDto> findAll(Sort sort);
  Optional<RsEventDto> findByTradeRank(int tradeRank);

  @Transactional
  void deleteAllByUserId(int userId);

  @Transactional
  void deleteAllByTradeRank(int tradeRank);
}
