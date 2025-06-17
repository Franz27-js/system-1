package DaFraDa.dobot.awattar;

import java.util.List;

public class AwattarApiTest {

	public static void main(String[] args) {
		List<PriceEntry> prices = AwattarApi.fetchMarketPrices();
		for (PriceEntry price : prices) {
			System.out.println(price.toString());
		}
	}
}
