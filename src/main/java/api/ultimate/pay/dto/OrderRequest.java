package api.ultimate.pay.dto;

@lombok.Data
public class OrderRequest {

	private int amount;
    private String currency = "INR";
    private String receipt;
}
