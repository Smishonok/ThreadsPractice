package com.valentinenikolaev.futureAndPromises;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args)
    throws IOException, ExecutionException, InterruptedException {

        System.out.println("Create arrays with entities names.");
        String typesGroup1[] = {"Zydrine", "Megacyte", "Mexallon"};
        String typesGroup2[] = {"Tritanium", "Pyerite", "Nocxium"};

        System.out.println("Crete executor, which will use 2 threads.");
        ExecutorService executor = Executors.newFixedThreadPool(2);

        System.out.println("Get the first entities list wrapped in the Future.");
        Future<List<Entity>> entityList1 = executor.submit(
                new EntityLoader(Arrays.asList(typesGroup1)));
        System.out.println("Get the second entities list wrapped in the Future.");
        Future<List<Entity>> entityList2 = executor.submit(
                new EntityLoader(Arrays.asList(typesGroup2)));

        System.out.println("Marge to lists in one.");
        List<Entity> entities = new ArrayList<>();
        entities.addAll(entityList1.get());
        System.out.println("Marge to lists in one 2.");
        entities.addAll(entityList2.get());

        entities.forEach(System.out::println);


        List<CompletableFuture<List<Entity>>> completableFutures = Stream.of(typesGroup1,
                                                                             typesGroup2)
                                                                         .map(Main::getFutureList)
                                                                         .collect(
                                                                                 Collectors.toList());

        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(
                completableFutures.toArray(CompletableFuture[]::new));

        List<Entity> entityList = completableFuture.thenApply(v->{
            List<Entity> completedFutureList = completableFutures.stream()
                                                                 .map(CompletableFuture::join)
                                                                 .flatMap(list->list.stream())
                    .collect(Collectors.toList());
            return completedFutureList;
        });

        executor.shutdown();
    }

    private static CompletableFuture<List<Entity>> getFutureList(String[] entitiesNames) {
        return CompletableFuture.supplyAsync(new EntityLoader(Arrays.asList(entitiesNames)));
    }


}
