package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SeleniumScheduledTask {

    static WebDriver driver;
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // Metodo ppal que llama a:  runSeleniumCode() para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Ejecutando el código de Selenium cada 5 minutos...");
                runSeleniumCode();
            }
        }, 0, 15, TimeUnit.MINUTES);
    }
    public static void runSeleniumCode() {
        int maxRetries = 2;     // Número máximo de intentos
        int contadorIntentos=1;       // Contador de intentos en el ciclo
        int IDcliente =0;
        String rutaArchivoClientes = "/Users/badir.realpe/Documents/Clientes.txt";

        try {
            validationCreationFile(rutaArchivoClientes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Lista de correos para asignar cita.
        List<String[]> listaClientes =  leerArchivoTxt(rutaArchivoClientes);
                //new CorreoConContraseña("anarosahoy@hotmail.com", "anahoyos")
                // new CorreoConContraseña("correo3@gmail.com", "contraseña3")
                // new CorreoConContraseña("juansilo0722@gmail.com", "juliansierra")

        // Leer el archivo de texto para obtener las direcciones de correo y contraseñas

        // Ciclo para recorrer el listado de correos.
        for (String[] datosCliente : listaClientes) {

            if(!datosCliente[2].equals("reprogramado")) {

                while (contadorIntentos <= maxRetries) {
                    try {
                        System.out.println("***********************************************************************");
                        System.out.println("Reprogramando a......:" + datosCliente[0]+ "  ((Intento #: " + (contadorIntentos)+"))");
                        System.out.println("***********************************************************************");
                        //System.out.println("Intento #: " + (contadorIntentos));

                        InstanciateTheDriver();

                        WebElement username = driver.findElement(By.id("user_email"));
                        WebElement pass = driver.findElement(By.id("user_password"));
                        WebElement botonIniciarSesion = driver.findElement(By.xpath("//input[@name='commit']"));
                        WebElement politicaPrivacidad = driver.findElement(By.xpath("//div[input[@id='policy_confirmed']]"));

                        // PAGINA PRINCIPAL
                        username.sendKeys(datosCliente[0]);
                        pass.sendKeys(datosCliente[1]);
                        politicaPrivacidad.click();
                        botonIniciarSesion.click();
                        sleepSeconds(5000);

                        /*String partes[] = new String[5];
                        partes[3] = "December";
                        partes[4] = "2028";*/


                        //Tomas las fechas actuales de la cita
                        WebElement citaActual = driver.findElement(By.xpath("//div[@class='application attend_appointment card success']//p[@class='consular-appt']"));
                        String datoCitaActual = citaActual.getText();
                        datoCitaActual = datoCitaActual.replace(",", "");
                        String partes[] = datoCitaActual.split(" ");


                        System.out.println("Dia Actual Cita...:" + partes[2]); // Dia de la cita actual
                        System.out.println("Mes Actual Cita...:" + partes[3]); // Mes de la cita actual
                        System.out.println("Año Actual Cita...:" + partes[4]); // Año de la cita actual
                        //sleep(2000);

                        WebElement botonContinuar = driver.findElement(By.xpath("//li[a[text()='Continuar']]"));
                        botonContinuar.click();
                        sleepSeconds(1000);

                        //menu programar cita primera vez
                        /*WebElement menuProgramarCita = driver.findElement(By.xpath("//*[@id='forms']/ul/li[1]"));
                        menuProgramarCita.click();
                        sleep(2000);*/

                        //boton de programar cita de primera vez.
                        /*WebElement botonProgramarCita = driver.findElement(By.cssSelector("a.button.small.primary.small-only-expanded"));
                        botonProgramarCita.click();
                        sleep(2000);*/



                        WebElement menuReprogramarCita = driver.findElement(By.xpath("//*[@id='forms']/ul/li[4]"));
                        menuReprogramarCita.click();
                        sleepSeconds(1000);

                        WebElement botonReprogramarCita = driver.findElement(By.xpath("/html/body/div[4]/main/div[2]/div[2]/div/section/ul/li[4]/div/div/div[2]/p[2]/a"));
                        botonReprogramarCita.click();
                        sleepSeconds(1000);

                        //driver.navigate().refresh();
                        //Campo para abrir el calendario

                        WebElement solicitantesFamilias = driver.findElement(By.xpath("//input[@data-disable-with='Continuar']"));
                        solicitantesFamilias.click();
                        sleepSeconds(1000);

                        // WARNING!!!!!  Es posible que el objeto fechaCitaEmbajada no exista.
                        WebElement fechaCitaEmbajada = driver.findElement(By.xpath("//*[@id='appointments_consulate_appointment_date']"));
                        scrollIntoView(fechaCitaEmbajada);
                        fechaCitaEmbajada.click();
                        sleepSeconds(1000);

                        String textoAnioCitaActualCalEmbajada;// Toma el año del primer mes del calendario de citas
                        WebElement anioCitaActualCalendarioEmbajada = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']"));
                        textoAnioCitaActualCalEmbajada = anioCitaActualCalendarioEmbajada.getText();

                        String textoMesCitaActualCal; // Toma el primer mes que aparece en el calendario
                        WebElement mesCitaActualCalendario = driver.findElement(By.xpath("//span[@class='ui-datepicker-month']"));
                        textoMesCitaActualCal = mesCitaActualCalendario.getText();

                        // Da click al icono de la flecha para avanzar al siguiente mes del calendario ESTA SENTENCIA NO DEBERIA ESTAR AQUI
                        WebElement iconoFlechaNextCalendarEmbajada = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/a"));
                        iconoFlechaNextCalendarEmbajada.click();

                        // Define el objeto iconoFlechaNextCalendarEmbajada para que sea null y pueda entrar al ciclo
                        //WebElement iconoFlechaNextCalendarEmbajada = null;
                        WebElement diaDisponibleCitaEmbajada= null;

                        String mesEnIngles = convertirMes(partes[3]);
                        //(No hay dia disponible en el calendario (diaDisponible==null) && el año de la cita actual (partes[4]) se MAYOR que el año que muesta el calendario (anioCitaActualCal)
                        // ó el año de la cita actual (partes[4]) se IGUAL que el año que muesta el calendario (anioCitaActualCal) && el mes de la cita actual (partes[3]) NO SEA el mes que muestra el calendario (mesCitaActualCal)
                        while (diaDisponibleCitaEmbajada == null && ((Integer.parseInt(partes[4]) > Integer.parseInt(textoAnioCitaActualCalEmbajada)) ||
                                ((Integer.parseInt(partes[4]) == Integer.parseInt(textoAnioCitaActualCalEmbajada)) && !mesEnIngles.equals(textoMesCitaActualCal)))) {


                            try {
                                diaDisponibleCitaEmbajada = driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//td[@data-handler='selectDay']"));
                                diaDisponibleCitaEmbajada.click();
                            } catch (WebDriverException e) {
                                diaDisponibleCitaEmbajada = null;
                            }

                            /*System.out.println("fecha actual: "+Integer.parseInt(partes[4])+" fecha cal: "+Integer.parseInt(anioCitaActualCalendarioEmbajada.getText()));
                            System.out.println("mes actual : "+partes[3]+ "mes cal: "+mesCitaActualCal);*/
                            iconoFlechaNextCalendarEmbajada = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/a"));
                            iconoFlechaNextCalendarEmbajada.click();
                            iconoFlechaNextCalendarEmbajada = null;
                            anioCitaActualCalendarioEmbajada = null;
                             sleepSeconds(70);

                            iconoFlechaNextCalendarEmbajada = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/a"));

                            anioCitaActualCalendarioEmbajada = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']"));
                            textoAnioCitaActualCalEmbajada = anioCitaActualCalendarioEmbajada.getText();

                            mesCitaActualCalendario = driver.findElement(By.xpath("//span[@class='ui-datepicker-month']"));
                            textoMesCitaActualCal = mesCitaActualCalendario.getText();

                            //System.out.println("El valor del año despues del null es: " + anioCitaActualCalendarioEmbajada.getText());
                            if(diaDisponibleCitaEmbajada!=null){
                                try {

                                   /* if(diaDisponibleCitaEmbajada == null){
                                        diaDisponibleCitaEmbajada = driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//td[@data-handler='selectDay']"));
                                        diaDisponibleCitaEmbajada.click();
                                        sleepSeconds(500);
                                    }*/
                                    sleepSeconds(500);
                                    WebElement listaHorasCitaEntrevista = driver.findElement(By.id("appointments_consulate_appointment_time"));
                                    listaHorasCitaEntrevista.click();

                                    WebElement horaCitaEntrevista = driver.findElement(By.xpath("//*[@id='appointments_consulate_appointment_time']/option[2]"));
                                    horaCitaEntrevista.click();
                                    sleepSeconds(1000);


                                    // Crear un objeto Select a partir del elemento listaHorasCitaEntrevista para seleccionar una opcion.
                                    Select select = new Select(listaHorasCitaEntrevista);
                                    // Seleccionar la primera opción por índice
                                    select.selectByIndex(1);
                                    sleepSeconds(1000);


                                    // CALENDARIO DE FECHA DE HUELLAS
                                    WebElement fechaCitaHuellas = driver.findElement(By.xpath("//*[@id='appointments_asc_appointment_date']"));
                                    fechaCitaHuellas.click();
                                    int contadorDiasDisponiblesCitaHuellas = 31;

                                    String textoAnioCitaCalendarioHuella;// Toma el año del primer mes del calendario de citas
                                    WebElement anioCitaCalendarioHuella = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']"));
                                    textoAnioCitaCalendarioHuella = anioCitaCalendarioHuella.getText();

                                    //Aqui se deberia incluir el ciclo hacia atras para buscar la fecha de huellas
                                    WebElement diaDisponibleCitaHuellas;
                                    try {
                                        diaDisponibleCitaHuellas = driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//td[@data-handler='selectDay']"));
                                        sleepSeconds(1000);
                                    } catch (WebDriverException e) {
                                        diaDisponibleCitaHuellas = null;
                                    }

                                    WebElement iconoFlechaNextCalendarHuellas = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/a"));

                                    while (diaDisponibleCitaHuellas == null && Integer.parseInt(textoAnioCitaActualCalEmbajada) >= Integer.parseInt(textoAnioCitaCalendarioHuella)){

                                        sleepSeconds(70);
                                        iconoFlechaNextCalendarHuellas.click();
                                        iconoFlechaNextCalendarHuellas = null;
                                        anioCitaCalendarioHuella = null;
                                        sleepSeconds(70);

                                        iconoFlechaNextCalendarHuellas = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/a"));
                                        anioCitaCalendarioHuella = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']"));
                                        try {
                                            diaDisponibleCitaHuellas = driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//td[@data-handler='selectDay']"));

                                            WebElement ultimoDiaDisponibleCitaHuellas = null;
                                            while (contadorDiasDisponiblesCitaHuellas > 0) {
                                                try {
                                                    ultimoDiaDisponibleCitaHuellas = driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//td[@data-handler='selectDay']/a[text()='" + contadorDiasDisponiblesCitaHuellas + "']"));
                                                    break;
                                                } catch (Exception e) {
                                                    contadorDiasDisponiblesCitaHuellas--;
                                                    //throw new RuntimeException(e);
                                                }
                                            }

                                            ultimoDiaDisponibleCitaHuellas.click();
                                            //  diaDisponibleCitaHuellas.click();
                                            sleepSeconds(1000);

                                            WebElement listaHorasCitaHuellas = driver.findElement(By.id("appointments_consulate_appointment_time"));
                                            listaHorasCitaHuellas.click();
                                            WebElement horaCitaHuellas = driver.findElement(By.xpath("//*[@id='appointments_asc_appointment_time']/option[2]"));
                                            horaCitaHuellas.click();
                                            sleepSeconds(1000);

                                            // Crear un objeto Select a partir del elemento listaHorasCitaEntrevista para seleccionar una opcion.
                                            Select select1 = new Select(listaHorasCitaHuellas);
                                            // Seleccionar la primera opción por índice
                                            select1.selectByIndex(1);
                                            sleepMinutes(1000);

                                            //Boton para programary reprogramar la cita por primera vez.
                                            WebElement botonProgrameLaCita = driver.findElement(By.xpath("//*[@id='appointments_submit']"));
                                            botonProgrameLaCita.click();
                                            sleepSeconds(1000);

                                            // Boton para confirmar la cita
                                            WebElement botonConfirmacionCita = driver.findElement(By.xpath("//div[@data-confirm-footer]//a[contains(text(),'Confirmar')]"));
                                            botonConfirmacionCita.click();
                                            sleepSeconds(1000);

                                            // Boton para no aceptar cargos adicionales
                                            WebElement botonNoGracias = driver.findElement(By.xpath("//button[contains(text(),'No gracias')]"));
                                            botonNoGracias.click();

                                            //Escribe en el archivo el correo del usuario al que le fue reagendada al cita.
                                            actualizarRegistro(listaClientes, IDcliente, rutaArchivoClientes);

                                        /*WebElement cajaTexto = driver.findElement(By.xpath("//*[@id='appointments_asc_appointment_time']/option[2]"));
                                        cajaTexto.click();
                                        sleepSeconds(2000);*/

                                        } catch (WebDriverException e) {
                                            diaDisponibleCitaHuellas = null;
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                } catch (WebDriverException e) {
                                    diaDisponibleCitaEmbajada = null;
                                }
                            }
                        }
                        // Si la cita se reagendo el break detiene el numero de intentos para ese cliente.
                        driver.quit();
                        contadorIntentos++;
                        sleepSeconds(2000);
                        //break;

                    } catch (WebDriverException e) {
                        driver.quit();
                        contadorIntentos++;
                        sleepSeconds(1);
                    }
                }
                //Reinicia el contador de intentos para el siguiente cliente.
                contadorIntentos=1;
                //Aumenta el contador del archivo para actualizar el siguiente cliente si se reprogramo correctamente el cliente en proceso.
                IDcliente=+1;
            }
            //System.out.println("cliente: "+datosCliente[0]);
        }
    }
    public static String convertirMes(String mes) {
        String mesIngles = "";
        switch (mes) {
            case "enero":
                mesIngles = "January";
                break;
            case "febrero":
                mesIngles = "February";
                break;
            case "marzo":
                mesIngles = "March";
                break;
            case "abril":
                mesIngles = "April";
                break;
            case "mayo":
                mesIngles = "May";
                break;
            case "junio":
                mesIngles = "June";
                break;
            case "julio":
                mesIngles = "July";
                break;
            case "agosto":
                mesIngles = "August";
                break;
            case "septiembre":
                mesIngles = "September";
                break;
            case "octubre":
                mesIngles = "October";
                break;
            case "noviembre":
                mesIngles = "November";
                break;
            case "diciembre":
                mesIngles = "December";
                break;
        }
        return mesIngles;
    }   // Leer el archivo de texto y almacenar los datos en una lista de arreglos de cadenas
    public static List<String[]> leerArchivoTxt(String rutaArchivo) {
        List<String[]> dataList = new ArrayList<>();
        String linea;
        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(" ");
                dataList.add(datos);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
    public static void actualizarRegistro(List<String[]> dataList, int indiceRegistro, String rutaArchivo) {
        try {
            if (indiceRegistro >= 0 && indiceRegistro < dataList.size()) {
                String[] registro = dataList.get(indiceRegistro);
                if (registro.length > 2) {
                    registro[2] = "reprogramado";
                }

                // Reconstruir el contenido del archivo con los registros actualizados
                StringBuilder sb = new StringBuilder();
                for (String[] datos : dataList) {
                    for (int i = 0; i < datos.length; i++) {
                        sb.append(datos[i]);
                        if (i < datos.length - 1) {
                            sb.append(" ");
                        }
                    }
                    sb.append("\n");
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo));
                bw.write(sb.toString());
                bw.close();

                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("Índice de registro fuera de rango.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void validationCreationFile(String rutaArchivo) throws IOException {
        // Creacion del objeto-archivo para guardar citas reagendadas - NO CREA UN ARCHIVO, solo un objeto que lo representa.
        File archivo = new File(rutaArchivo);
        // Verificar si el archivo ya existe.
        if (!archivo.exists()) {
            System.out.println("El archivo txt NO existe o no esta en la ruta especificada");
            /*FileWriter escritor = new FileWriter(rutaArchivo,true);
            escritor.write("Clientes con programaciones hechas\n");
            escritor.close();*/
        } /*else {
            System.out.println("El archivo txt NO existe.");
        }*/
    }
    private static void InstanciateTheDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        String url = "https://ais.usvisa-info.com/es-co/niv/users/sign_in";
        driver.get(url);
        sleepSeconds(2000);
    }
    private static void sleepSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void sleepMinutes(long minutes) {
        try {
            Thread.sleep(minutes);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        sleepSeconds(2000); // Espera opcional para asegurarse de que la página se haya desplazado completamente
    }
}
