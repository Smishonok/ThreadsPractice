package com.valentinenikolaev.futureAndPromises;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class EntityLoader implements Callable<List<Entity>>, Supplier<List<Entity>> {

    private URL url;
    HttpURLConnection connection;
    private String requestBase = "https://www.fuzzwork.co.uk/api/typeid2.php?typename=";

    public EntityLoader() {
    }

    public EntityLoader(List<String> typeNames)  {
        String fullRequestString = getRequestString(typeNames);
        try {
            this.url        = new URL(fullRequestString);
            this.connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRequestedTypesList(List<String> typeNames) throws IOException {
        String fullRequestString = getRequestString(typeNames);
        this.url        = new URL(fullRequestString);
        this.connection = (HttpURLConnection) url.openConnection();
    }

    private String getRequestString(List<String> typeNames) {
        if (typeNames.size() != 0) {
            return createRequestString(typeNames);
        } else {
            throw new IllegalArgumentException("List of requested entities can`t be empty.");
        }
    }

    private String createRequestString(List<String> typeNames) {
        StringBuilder requestStringBuilder = new StringBuilder(requestBase);
        for (int i = 0; i < typeNames.size(); i++) {
            if (i == typeNames.size() - 1) {
                requestStringBuilder.append(typeNames.get(i));
            } else {
                requestStringBuilder.append(typeNames.get(i)).append("|");
            }
        }
        return requestStringBuilder.toString();
    }

    @Override
    public List<Entity> call() throws Exception {
        System.out.println("Start loading.");
        BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String response = new String(inputStream.readAllBytes());
        inputStream.close();

        Thread.sleep(5000);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Entity>>() {}.getType();
        System.out.println("End of loading.");
        return gson.fromJson(response, type);
    }

    @Override
    public List<Entity> get() {
        List<Entity> entities = new ArrayList<>();
        try {
            entities = call();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return entities;
    }
}

