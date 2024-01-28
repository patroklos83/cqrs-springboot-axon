package com.patroclos.cqrs;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(0)
class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.info("****************************************");
    log.info("*                                      *");
    log.info("*      CQRS service started            *");
    log.info("*                                      *");
    log.info("****************************************");
  }

}