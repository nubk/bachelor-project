package app.controller;

import app.model.ExerciseInfo;
import app.model.PivotAttempt;
import app.model.UserInfo;
import app.service.AttemptService;
import app.service.UserService;
import com.google.common.collect.ImmutableList;
import io.javalin.Context;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsController {

    public static List<ExerciseInfo> getExerciseInfo(List<UserInfo> userInfoList) {
        // todo: consider getting IDs some other way ?
        return userInfoList.get(0).getExerciseToSolved().keySet().stream() // get exercise-ids from first student
                .map(exerciseId -> new ExerciseInfo(exerciseId, userInfoList)) // create exercise
                .sorted(Comparator.comparing(ExerciseInfo::getExerciseId))
                .collect(Collectors.toList());
    }

    public static void getStudentInfo(Context ctx) {
        List<UserInfo> userInfoList = ImmutableList.of(UserService.getUserInfoByUserId(ctx.param(":student-id")));
        ctx.json(StatisticsController.getExerciseInfo(userInfoList));
    }

    public static void getExerciseInfo(Context ctx) {
        ctx.json(StatisticsController.getExerciseInfo(UserService.getAllUserInfo()));
    }

    public static void getAllUserInfo(Context ctx) {
        ctx.json(UserService.getAllUserInfo());
    }

    public static void getPivotAttempts(Context ctx) {
        ctx.json(AttemptService.getAttempts().stream().map(PivotAttempt::new).collect(Collectors.toList()));
    }
}
