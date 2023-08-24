package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {
    private FileSize fileSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        fileSize = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if(file.getSize() > fileSize.maxFileSize()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            fileSize.message())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
