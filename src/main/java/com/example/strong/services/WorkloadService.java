package com.example.strong.services;

import com.example.strong.entities.Training;

import java.util.Collection;

public interface WorkloadService {
    void create(Training training);
    void delete(Collection<Training> trainings);
}
