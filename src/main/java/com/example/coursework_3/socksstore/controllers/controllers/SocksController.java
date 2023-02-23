package com.example.coursework_3.socksstore.controllers.controllers;

import com.example.coursework_3.socksstore.controllers.models.Socks;
import com.example.coursework_3.socksstore.controllers.models.enums.Color;
import com.example.coursework_3.socksstore.controllers.models.enums.Size;
import com.example.coursework_3.socksstore.controllers.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("id/socks")
@Tag(name = "Носки", description = ": ")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping()
    @Operation(
            summary = "Добавление носков",
            description = "Варианты цветов: RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE, BROWN, PINK, VIOLET, GREY, BLACK, WHITE<br>" +
                    "Варианты размеров: S, M, L, XL, XXL<br>" +
                    "Содержание хлопка от 1 до 100"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось добавить",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Socks.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Данные указаны не верно",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Упс.. что то пошло не так :(",
                    content = @Content()
            )
    })
    public ResponseEntity<Socks> addSocks(@Valid @RequestBody Socks socks){
        return ResponseEntity.ok(socksService.addSocks(socks));

    }

    @GetMapping("all")
    @Operation(
            summary = "Носки в наличии"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Запрос выполнен",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
            )
    )
    public ResponseEntity<List<Socks>> getAllSocks(){
        return ResponseEntity.ok(socksService.getAllSocks());
    }

    @GetMapping()
    @Operation(
            summary = "Получение кол-ва носков по фильтрам"
    )
    @Parameters(value = {
            @Parameter(
                    name = "color",
                    schema = @Schema(implementation = Color.class)
            ),
            @Parameter(
                    name = "size",
                    schema = @Schema(implementation = Size.class)
            ),
            @Parameter(
                    name = "cottonMin",
                    description = "в диапазоне от 1 до 100"
            ),
            @Parameter(
                    name = "cottonMax",
                    description = "в диапазоне от 1 до 100"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {@ExampleObject(value = "20")}
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Данные указаны не верно",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Упс.. что то пошло не так :(",
                    content = @Content()
            )
    })
    public ResponseEntity<Integer> getSocks(@RequestParam com.example.coursework_3.socksstore.controllers.models.enums.Color color,
                                            @RequestParam com.example.coursework_3.socksstore.controllers.models.enums.Size size,
                                            @RequestParam(defaultValue = "1") int cottonMin,
                                            @RequestParam(defaultValue = "100") int cottonMax){
        Integer quantity = socksService.getSocks(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(quantity);
    }

    @PutMapping()
    @Operation(
            summary = "Выдача носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Socks.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Упс.. что то пошло не так :(",
                    content = @Content()
            )
    })
    public ResponseEntity<Socks> updateSocks(@Valid @RequestBody Socks socks){
        if (socksService.updateSocks(socks)){
            return ResponseEntity.ok(socks);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping()
    @Operation(
            summary = "Списание носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Socks.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Упс.. что то пошло не так :(",
                    content = @Content()
            )
    })
    public ResponseEntity<Socks> removeSocks(@RequestBody Socks socks){
        if (socksService.removeSocks(socks)){
            return ResponseEntity.ok(socks);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
