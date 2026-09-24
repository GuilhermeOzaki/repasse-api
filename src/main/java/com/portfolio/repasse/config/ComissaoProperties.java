package com.portfolio.repasse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "repasse.comissao")
public record ComissaoProperties(BigDecimal entregaIfood,
                                 BigDecimal entregaPropria)
{

}
