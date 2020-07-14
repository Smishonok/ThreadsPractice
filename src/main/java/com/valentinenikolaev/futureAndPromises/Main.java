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
        ExecutorService executor = Executors.newFixedThreadPool(4);

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


        //CompletableFuture combining two tasks (options A).
        List<CompletableFuture<List<Entity>>> completableFutures;
        completableFutures = Stream.of(typesGroup1, typesGroup2)
                                   .map(gr->Main.getFutureList(gr, executor))
                                   .collect(Collectors.toList());
        //        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(
        //                completableFutures.toArray(CompletableFuture[]::new));

        //CompletableFuture combining two tasks (options B).
        CompletableFuture<List<Entity>> completableArray[] = new CompletableFuture[0];
        Stream.of(typesGroup1, typesGroup2)
              .map(gr->Main.getFutureList(gr, executor))
              .collect(Collectors.toList())
              .toArray(completableArray);
        //CompletableFuture<Void> completableFuture = CompletableFuture.allOf(completableArray);

        //CompletableFuture combining two tasks (options C).
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(
                getFutureList(typesGroup1, executor), getFutureList(typesGroup2, executor));


        CompletableFuture<List<Entity>> completedFuture = completableFuture.thenApply(aVoid->{
            List<Entity> entityList = completableFutures.stream()
                                                        .map(CompletableFuture::join)
                                                        .flatMap(List::stream)
                                                        .collect(Collectors.toList());
            return entityList;
        });


        completedFuture.get().forEach(System.out::println);


        CompletableFuture<List<Entity>> firstEntityList = CompletableFuture.supplyAsync(
                new EntityLoader(List.of(typesGroup1)));
        CompletableFuture<List<Entity>> secondEntityList = CompletableFuture.supplyAsync(
                new EntityLoader(List.of(typesGroup2)));
        CompletableFuture<List<Entity>> totalEntityList = firstEntityList.thenCombine(
                secondEntityList, (f1, f2)->{
                    f1.addAll(f2);
                    return f1;
                });

        totalEntityList.get().forEach(System.out::println);

        executor.shutdown();
    }

    private static CompletableFuture<List<Entity>> getFutureList(String[] entitiesNames,
                                                                 Executor executor) {
        return CompletableFuture.supplyAsync(new EntityLoader(Arrays.asList(entitiesNames)),
                                             executor);
    }


}
