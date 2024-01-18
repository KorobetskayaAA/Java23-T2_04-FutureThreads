package ru.teamscore.java23.getasync;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class DownloadMain {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        HttpClient client = HttpClient.newBuilder()
                .build();

        Path downloadDir = Path.of(System.getProperty("user.home"), "downloads");
        if (Files.notExists(downloadDir)) {
            Files.createDirectory(downloadDir);
        }

        while (true) {
            System.out.print("Введите ссылку для скачивания файла: ");
            String url = sc.nextLine();
            if (url.isBlank()) {
                break;
            }
            String fileName = Path.of(url).getFileName().toString();
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .build();
                CompletableFuture<HttpResponse<Path>> result = client
                        .sendAsync(request,
                                HttpResponse.BodyHandlers.ofFile(downloadDir.resolve(fileName)));

                System.out.printf("\u001B[33mНачинаем загрузку файла %s\u001B[0m\n", fileName);
                result.thenAccept((response) -> {
                            if (response.statusCode() == 200) {
                                System.out.printf("\u001B[32mФайл %s был успешно загружен\u001B[0m\n",
                                        response.body());
                            } else {
                                System.err.printf("При попытке скачивания файла %s сервер вернул код %d\n",
                                        fileName,
                                        response.statusCode());
                            }
                        })
                        .exceptionally((e) -> {
                            System.err.printf("Не удалось загрузить файл %s: %s\n",
                                    fileName,
                                    e.getMessage());
                            return null;
                        });
            } catch (URISyntaxException e) {
                System.err.println("Некорректный URL: " + e.getMessage());
            }
        }
    }
}
