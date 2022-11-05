package com.example.testsecurity2dbthemeleaf.repository;

import com.example.testsecurity2dbthemeleaf.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface RepositoryLogs extends JpaRepository<Logs, Long>{

}
