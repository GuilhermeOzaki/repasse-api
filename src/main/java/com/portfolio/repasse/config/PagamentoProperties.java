package com.portfolio.repasse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "repasse.pagamento")
public record PagamentoProperties(BigDecimal pix,
                                  BigDecimal credito,
                                  BigDecimal debito)
{
}
