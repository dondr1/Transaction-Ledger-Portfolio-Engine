package  com.dhenamuthan.ledger.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PortfolioResponse {

    private BigDecimal cashBalance;
    private Map<String, BigDecimal> holdings;

    public PortfolioResponse(BigDecimal cashBalance, Map<String, BigDecimal> holdings) {
        this.cashBalance = cashBalance;
        this.holdings = holdings;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public Map<String, BigDecimal> getHoldings() {
        return holdings;
    }
}
