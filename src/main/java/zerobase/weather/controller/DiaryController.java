package zerobase.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @Operation(summary = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장.", description = "이것은 노트.")
    @PostMapping("/creat/diary")
    void createDiary(@RequestParam
                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     @Parameter(example = "2023-11-22") LocalDate date,
                     @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    @Operation(summary = "선택한 날짜의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diary")
    List<Diary> ReadDiary(@RequestParam
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                          @Parameter(example = "2023-11-22")
                          LocalDate date) {
        return diaryService.readDiary(date);
    }

    @Operation(summary = "선택한 기간 중의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diaries")
    List<Diary> ReadDiaries(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @Parameter(description = "조회할 기간의 첫번째날", example = "2023-11-22") LocalDate startDate,
                            @RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @Parameter(description = "조회할 기간의 마지막날", example = "2023-11-22") LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @Operation(summary = "일기를 업데이트 합니다.")
    @PutMapping("/update/diary")
    public void updateDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @Parameter(example = "2023-11-22") LocalDate date,
                            @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    @Operation(summary = "일기를 삭제 합니다.")
    @DeleteMapping("/delete/diary")
    public void deleteDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @Parameter(description = "삭제할 일기의 날짜", example = "2023-11-22") LocalDate date) {
        diaryService.deleteDiary(date);
    }


}
