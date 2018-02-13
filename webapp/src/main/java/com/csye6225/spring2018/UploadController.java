package com.csye6225.spring2018;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class UploadController {
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/tmp/cloudpic/";
    //private static String UPLOADED_FOLDER = "../images/";

    @Value("${app.user.root}")
    private String userRoot;

    @GetMapping("/upload")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/welcome.html";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/welcome.html";
    }



    @RequestMapping(value = "/user/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String fileName = formatter.format(Calendar.getInstance().getTime()) + "_thumbnail.jpg";
        //User user = userService.getLoggedInUser();
        if (!file.isEmpty()) {
            try {
                String saveDirectory = userRoot + File.separator + user.getId() + File.separator;
                File test = new File(saveDirectory);
                if(!test.exists()) {
                    test.mkdirs();
                }

                byte[] bytes = file.getBytes();

                ByteArrayInputStream imageInputStream = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(imageInputStream);
                BufferedImage thumbnail = Scalr.resize(image, 200);

                File thumbnailOut = new File(saveDirectory + fileName);
                ImageIO.write(thumbnail, "png", thumbnailOut);

                userService.updateProfilePicture(user, fileName);
                userService.getLoggedInUser(true); //Force refresh of cached User
                System.out.println("Image Saved::: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/user/edit/" + user.getId();
    }

    @RequestMapping(value="/user/profile-picture", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] profilePicture() throws IOException {
        User u = userService.getLoggedInUser();
        String profilePicture = userRoot + File.separator + u.getId() + File.separator + u.getProfilePicture();
        if(new File(profilePicture).exists()) {
            return IOUtils.toByteArray(new FileInputStream(profilePicture));
        } else {
            return null;
        }
    }
}
