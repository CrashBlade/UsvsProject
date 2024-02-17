package org.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest {
/*
    int maxRetries = 3; // Número máximo de intentos
    int retryCount = 0;
    WebDriver driver;


    public void loginTest() {

        List<CorreoConContraseña> listaCorreos = Arrays.asList(
                new CorreoConContraseña("juansilo0722@gmail.com", "juliansierra"),
                new CorreoConContraseña("correo2@gmail.com", "contraseña2")
           //     new CorreoConContraseña("correo3@gmail.com", "contraseña3")
        );


        for (CorreoConContraseña correo : listaCorreos) {

            System.out.println("Starting "+correo.getCorreo()+" attempt");
            retryCount = 0;

            while (retryCount < maxRetries) {
                try {

                    System.out.println("Attempt: "+(retryCount+1));
                    //		Create driver
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-extensions");

                    driver = new ChromeDriver(options);
                    //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    String url = "https://ais.usvisa-info.com/es-co/niv/users/sign_in";
                    driver.get(url);


                    WebElement username = driver.findElement(By.id("user_email"));
                    WebElement pass = driver.findElement(By.id("user_password"));
                    WebElement iniciar = driver.findElement(By.xpath("//input[@name='commit']"));
                    WebElement poli = driver.findElement(By.xpath("//div[input[@id='policy_confirmed']]"));

                    username.sendKeys(correo.getCorreo());
                    pass.sendKeys(correo.getContraseña());
                    poli.click();
                    iniciar.click();
                    sleep(1000);

                    WebElement citaActual = driver.findElement(By.xpath("//div[@class='application attend_appointment card success']//p[@class='consular-appt']"));
                    String datoCitaActual = citaActual.getText();
                    datoCitaActual = datoCitaActual.replace(",","");
                    String partes[] = datoCitaActual.split(" ");

                    System.out.println("1...:"+partes[2]);
                    System.out.println("2...:"+partes[3]);
                    System.out.println("3...:"+partes[4]);
                    sleep(5000);

                    WebElement conti = driver.findElement(By.xpath("//li[a[text()='Continuar']]"));
                    conti.click();

                    WebElement repro = driver.findElement(By.xpath("//*[@id='forms']/ul/li[4]"));
                    repro.click();
                    sleep(1000);

                    WebElement reprocita = driver.findElement(By.xpath("/html/body/div[4]/main/div[2]/div[2]/div/section/ul/li[4]/div/div/div[2]/p[2]/a"));
                    reprocita.click();
                    sleep(1000);

                    WebElement fechaDeLaCita = driver.findElement(By.xpath("//*[@id='appointments_consulate_appointment_date']"));
                    fechaDeLaCita.click();

                    WebElement anioCitaActualCalendario = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']"));

                    int nextCalendar;
                    while (Integer.parseInt(partes[4])>=Integer.parseInt(anioCitaActualCalendario.getText())){
                        sleep(2000);
                        WebElement next = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/a"));
                        next.click();
                        next=null;
                        sleep(1000);
                    }
                    break;

                }catch (WebDriverException e){
                    driver.quit();
                    retryCount++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }
    }
    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
*/
}
