package ru.aicontest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.service.SolutionService;

@RestController
@RequestMapping("/api/1")
public class SolutionController extends ApiController {
    private final SolutionService solutionService;

    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

//    @PostMapping("submit")
//    public void handleFileUpload(@RequestParam("file") MultipartFile file,
//                                 @RequestParam("language") String language, @ModelAttribute("user") BaseUserEntity user) {
//        System.out.println("file" + file.toString());
//        System.out.println("lang" + language);
//        if (file != null && !file.isEmpty() && language != null) {
//            try {
//                byte[] bytes = file.getBytes();
//                SolutionLanguage solutionLanguage = SolutionLanguage.cpp;
//                System.out.println("BaseUserEntity login - " + user.getLogin());
//                long solutionId = solutionService.upload(user, solutionLanguage);
//                System.out.println("Successfully created solution!");
//                TcpSocketSingleton.getInstance();//.sendFile(bytes, solutionId);
//                System.out.println("Successfully sent solution!");
//            } catch (IOException e) {
//                throw new NoSuchResourceException(e.getMessage());
//            }
//        } else {
//            System.out.println("Error in input data");
//            throw new NoSuchResourceException("Upload a correct file!");
//        }
//    }
}
