package com.example.a7minutesworkout

// this will be accessible through out the entire project

object constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel>{

        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingjacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingjacks)

        val lunges = ExerciseModel(
            2,
            "Lunges",
            R.drawable.lunges,
            false,
            false
        )
        exerciseList.add(lunges)

        val highknees = ExerciseModel(
            3,
            "High Knees",
            R.drawable.high_knees,
            false,
            false
        )
        exerciseList.add(highknees)

        val Plank = ExerciseModel(
            4,
            "Planks",
            R.drawable.plank,
            false,
            false
        )
        exerciseList.add(Plank)

        val Pushups = ExerciseModel(
            5,
            "Push Ups",
            R.drawable.push_ups,
            false,
            false
        )
        exerciseList.add(Pushups)

        val SidePlank = ExerciseModel(
            6,
            "Side Planks",
            R.drawable.side_plank,
            false,
            false
        )
        exerciseList.add(SidePlank)

        val Squat = ExerciseModel(
            7,
            "Squat",
            R.drawable.squat,
            false,
            false
        )
        exerciseList.add(Squat)

        val StepUpOnChair = ExerciseModel(
            8,
            "Step Up On Chair",
            R.drawable.step_up_on_chair,
            false,
            false
        )
        exerciseList.add(StepUpOnChair)

        val Triceps = ExerciseModel(
            9,
            "Triceps",
            R.drawable.triceps,
            false,
            false
        )
        exerciseList.add(Triceps)

        val WallSit = ExerciseModel(
            10,
            "Wall Sit",
            R.drawable.wall_sit,
            false,
            false
        )
        exerciseList.add(WallSit)

        val PushUpsRotation = ExerciseModel(
            11,
            "Push Ups and Rotation",
            R.drawable.push_up_rotation,
            false,
            false
        )
        exerciseList.add(PushUpsRotation)



        return exerciseList

    }

}