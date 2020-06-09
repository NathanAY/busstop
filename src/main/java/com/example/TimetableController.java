package com.example;

import com.example.domain.Service;
import com.example.service.FileService;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableController {

    private final FileService fileService;

    public TimetableController(final FileService fileService) {
        this.fileService = fileService;
    }

    public void run(final String path) {
        List<String> inputData = fileService.readAllLine(path);
        List<Service> services = convertInputToServiceList(inputData);
        process(services);
        List<String> lines = formatToLines(services);
        fileService.save(lines, path.substring(0, path.lastIndexOf("\\")) + "\\output.txt");
    }

    private void process(final List<Service> services) {
        services.sort(Comparator.comparing(Service::getDeparture));
        services.removeIf(service -> service.getDeparture().until(service.getArrival(), ChronoUnit.HOURS) >= 1);
        services.removeIf(s -> hasSamePoshService(s, services));
        services.removeIf(service -> hasMoreEfficient(service, services));
    }

    private List<Service> convertInputToServiceList(final List<String> inputData) {
        return inputData.stream()
                .map(s -> s.split(" "))
                .map(arr -> new Service(arr[0], LocalTime.parse(arr[1]), LocalTime.parse(arr[2])))
                .collect(Collectors.toList());
    }

    private boolean hasSamePoshService(final Service service, final List<Service> serviceList) {
        return serviceList.stream()
                .anyMatch(s -> !s.equals(service)
                        && s.getCompany().equals("Posh")
                        && s.getDeparture().equals(service.getDeparture())
                        && s.getArrival().equals(service.getArrival()));
    }

    private boolean hasMoreEfficient(final Service service, final List<Service> serviceList) {
        return serviceList.stream()
                .anyMatch(s -> {
                    if (s.getDeparture().equals(service.getDeparture())) {
                        return s.getArrival().isBefore(service.getArrival());
                    }
                    if (s.getDeparture().isAfter(service.getDeparture())) {
                        return s.getArrival().isBefore(service.getArrival())
                                || s.getArrival().equals(service.getArrival());
                    }
                    return false;
                });
    }

    private List<String> formatToLines(final List<Service> services) {
        List<String> lines = new ArrayList<>();
        services.stream()
                .filter(s -> s.getCompany().equals("Posh"))
                .forEach(s -> lines.add(s.getCompany() + " " + s.getDeparture() + " " + s.getArrival()));
        lines.add("");
        services.stream()
                .filter(s-> s.getCompany().equals("Grotty"))
                .forEach(s -> lines.add(s.getCompany() + " " + s.getDeparture() + " " + s.getArrival()));
        return lines;
    }

}
