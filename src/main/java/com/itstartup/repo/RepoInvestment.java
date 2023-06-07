package com.itstartup.repo;

import com.itstartup.models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoInvestment extends JpaRepository<Investment, Long> {
}
