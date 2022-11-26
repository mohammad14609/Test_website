package smartbuy_website;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class smartbuy_testing {
	public WebDriver driver;
	public int numbersOfTrys = 1000;
	SoftAssert checking_process = new SoftAssert();
	public int item_in_inventory = 20;

	@BeforeTest()

	public void before_starting_testing() throws InterruptedException {
		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://smartbuy-me.com/smartbuystore/"); // open website
		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click(); // change the language
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,950)");
		Thread.sleep(1000);

	}

	@Test(priority = 1)
	public void testing_add_item_cart_Huawei_Tablet_MatePad_2022_LTE_MatteGrey() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		for (int itemsAdded = 0; itemsAdded < numbersOfTrys; itemsAdded++) {

			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[4]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();
			String msg_popup = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[1]")).getText();

			if (msg_popup.contains("Sorry, there is insufficient stock for your cart.")) {
				numbersOfTrys = itemsAdded;
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();
			} else {
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();
			}

		}

	}

	@Test(priority = 2)
	public void checking_price() {
		driver.navigate().back();
		String single_item_price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[4]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();
		String[] update_single_price = single_item_price.split("JOD");
		String update_single_pric2 = update_single_price[0].trim();
		Double final_price_single_item = Double.parseDouble(update_single_pric2);
		System.out.println("==========final price for single item ======");
		System.out.println(final_price_single_item);
		System.out.println("===========================");
		System.out.println(final_price_single_item * numbersOfTrys);
		System.out.println("===========================");

	}

	@Test(priority = 3)
	public void checking_single_price_with_discount() {

		String single_price_tab = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[4]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();
		String[] update_single_price_test2 = single_price_tab.split("JOD");
		String update_single_pric2_test2 = update_single_price_test2[0].trim();
		Double final_price_single_item_test2 = Double.parseDouble(update_single_pric2_test2);
		System.out.println("==========final price for single item ======");
		System.out.println(final_price_single_item_test2);
		String orignal_item_price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[4]/div/div[2]/div[2]/div/div/span[2]"))
				.getText();
		System.out.println("===========original price================");
		String[] update_original_price_test2 = orignal_item_price.split("JOD");
		String update_original_pric2_test2 = update_original_price_test2[0].trim();
		Double final_price_original_item_test2 = Double.parseDouble(update_original_pric2_test2);
		System.out.println(final_price_original_item_test2);
		String percantge_discount_single_item = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[4]/div/div[2]/div[2]/div/div/span[1]"))
				.getText();
		System.out.println("===========%discount================");
		String[] percntage_remove = percantge_discount_single_item.split("%");
		String update_percantage = percntage_remove[0].trim();
		Double final_percntage_value = Double.parseDouble(update_percantage) / 100.0;
		System.out.println(final_percntage_value);
		System.out.println("=========calculation % with orinial price to give us the single price ========");
		System.out.println(final_price_original_item_test2 - (final_price_original_item_test2 * final_percntage_value));
		checking_process.assertEquals(
				final_price_original_item_test2 - (final_price_original_item_test2 * final_percntage_value),
				final_price_single_item_test2);
		checking_process.assertAll();
	}

}
