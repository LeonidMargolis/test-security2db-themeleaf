package com.example.testsecurity2dbthemeleaf.service;

import com.example.testsecurity2dbthemeleaf.entity.Logs;
import com.example.testsecurity2dbthemeleaf.repository.RepositoryLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class LogsSrviceimpl implements LogsService{
    @Override
    public void info(String text) {
        Logs logs = new Logs();
        logs.setText(text);

        Date date = new Date();

        logs.setTime(date.toString());
        repositoryLogs.save(logs);
    }
    private final RepositoryLogs repositoryLogs;

    @Autowired
    public LogsSrviceimpl(RepositoryLogs repositoryLogs){
        this.repositoryLogs = repositoryLogs;
    }

}
