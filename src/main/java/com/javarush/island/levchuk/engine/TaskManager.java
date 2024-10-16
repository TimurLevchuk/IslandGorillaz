package com.javarush.island.levchuk.engine;

import com.javarush.island.levchuk.map.IslandMap;
import com.javarush.island.levchuk.services.EatingService;
import com.javarush.island.levchuk.services.MoveService;
import com.javarush.island.levchuk.services.ReproduceService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;


public class TaskManager {
    public void eatAllInIsland(IslandMap islandMap, EatingService eatingService, ExecutorService executorService) throws InterruptedException {

        List<Callable<Void>> tasks = Arrays.stream(islandMap.getIslandMap())
                .flatMap(Arrays::stream).map(cell -> (Callable<Void>) () -> {
            eatingService.eatAllInCell(cell);
            cell.getLock().unlock();
            return null;
        }).toList();
        executorService.invokeAll(tasks);
    }

    public void moveAllInIsland(IslandMap islandMap, MoveService moveService, ExecutorService executorService) throws InterruptedException {
        List<Callable<Void>> tasks = Arrays.stream(islandMap.getIslandMap())
                .flatMap(Arrays::stream).map(cell -> (Callable<Void>) () -> {

            moveService.moveAllInCall(cell);

            return null;
        }).toList();
        executorService.invokeAll(tasks);
    }

    public void reproduceAllInIsland(IslandMap islandMap, ReproduceService reproduceService, ExecutorService executorService) throws InterruptedException {
        List<Callable<Void>> tasks = Arrays.stream(islandMap.getIslandMap())
                .flatMap(Arrays::stream).map(cell -> (Callable<Void>) () -> {
            reproduceService.reproduceAllInCall(cell);

            return null;

        }).toList();
        executorService.invokeAll(tasks);
    }
}
