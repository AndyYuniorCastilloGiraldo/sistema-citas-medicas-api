package com.andycg.citas.service.serviceImpl;

import com.andycg.citas.model.Cita;
import com.andycg.citas.service.SerializationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SerializationServiceImpl implements SerializationService {

    private static final String FILE_PATH = "archivos/citas.ser";
    private final ObjectMapper objectMapper;

    public SerializationServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void guardarCita(Cita cita) {
        List<Cita> citas = leerCitas();
        citas.add(cita);

        try {
            Path path = Paths.get(FILE_PATH);
            Files.createDirectories(path.getParent());

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), citas);
            log.info("Cita respaldada exitosamente en formato JSON en {}", FILE_PATH);

        } catch (IOException e) {
            log.error("Error al respaldar la cita en archivo JSON: {}", e.getMessage());
        }
    }

    private List<Cita> leerCitas() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<Cita>>() {
            });
        } catch (IOException e) {
            log.warn("No se pudo leer el archivo JSON o está vacío, iniciando nueva lista. Error: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
