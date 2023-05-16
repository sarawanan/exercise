package com.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepo extends JpaRepository<Exercise, String> {
    List<Exercise> findExerciseByCode(String code);
}
