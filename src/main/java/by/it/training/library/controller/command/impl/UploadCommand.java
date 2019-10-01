package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.UserType;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;
import java.util.Set;

public class UploadCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.GUEST);
    }

    @Override
    public void doDefault(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        System.out.println("description " + request.getParameter("description"));
        System.out.println("file " + request.getParameter("file"));
        try {
            Part part = request.getPart("file"); // Retrieves <input type="file" name="file">
            System.out.println("getSubmittedFileName()" + part.getSubmittedFileName());
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

            System.out.println("fileName " + fileName);
            try (InputStream input = part.getInputStream()) {
                System.out.println("input " + input.available());
                String upload = request.getServletContext().getInitParameter("upload");//??? getServletConfig()
                System.out.println("upload " + upload);
                File tempFile = File.createTempFile("xxx_", "_zzz", new File(upload));
                System.out.println("tempFile: " + tempFile);
                Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
