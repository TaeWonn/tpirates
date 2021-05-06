package com.test.persistence;

import com.test.domain.Shop;
import com.test.persistence.custom.ShopRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>
                                        , ShopRepositoryCustom {

}
