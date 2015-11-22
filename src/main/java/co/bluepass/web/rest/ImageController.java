package co.bluepass.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import co.bluepass.domain.Image;
import co.bluepass.exception.ImageNotFoundException;
import co.bluepass.service.ImageService;

/**
 * The type Image controller.
 */
/*import kr.co.campusshop.config.PropertyPlaceholderConfig;
import kr.co.campusshop.image.exception.ImageNotFoundException;
import kr.co.campusshop.image.service.ImageService;
import kr.co.campusshop.model.Image;
*/
@Controller
@RequestMapping
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    /**
     * The constant VIEW_IMAGE_UPLOAD.
     */
    public static final String VIEW_IMAGE_UPLOAD 	= "image/index";
    /**
     * The constant VIEW_IMAGE_TEST.
     */
    public static final String VIEW_IMAGE_TEST 	= "image/test";

	@Inject
	private Environment env;

    @Autowired
    private ImageService imageService;

	private String fileUploadDirectory;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        this.fileUploadDirectory = env.getProperty("file.upload.directory");
    }

    /**
     * Index string.
     *
     * @return the string
     */
    @RequestMapping("/image")
    public String index() {
        LOGGER.debug("ImageController home");
        return VIEW_IMAGE_UPLOAD;
    }

    /**
     * Test string.
     *
     * @return the string
     */
    @RequestMapping("/image/test")
    public String test() {
    	LOGGER.debug("ImageController home test");
    	return VIEW_IMAGE_TEST;
    }

    /**
     * List map.
     *
     * @return the map
     */
    @RequestMapping(value = "/image/upload", method = RequestMethod.GET)
    public @ResponseBody Map list() {
        LOGGER.debug("uploadGet called");
        List<Image> list = imageService.list();

        for(Image image : list) {
            image.setUrl("/image/picture/"+image.getId());
            image.setThumbnailUrl("/image/thumbnail/"+image.getId());
            image.setDeleteUrl("/image/delete/"+image.getId());
            image.setDeleteType("DELETE");
        }

        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        LOGGER.debug("Returning: {}", files);
        return files;
    }

    /**
     * Upload map.
     *
     * @param request  the request
     * @param response the response
     * @return the map
     */
    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    public @ResponseBody Map upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("uploadPost called");
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        List<Image> list = new LinkedList<>();

        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            LOGGER.debug("Uploading {}", mpf.getOriginalFilename());

            String newFilenameBase = UUID.randomUUID().toString();
            String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFilename = newFilenameBase + originalFileExtension;
            String storageDirectory = fileUploadDirectory;
            String contentType = mpf.getContentType();

            File newFile = new File(storageDirectory + "/" + newFilename);
            File thumbnailFile = null;
            Image image = null;

            try {
                mpf.transferTo(newFile);

                thumbnailFile = imageService.makeThumbnailFile(newFile);

                image = Image.getBuilder(mpf.getOriginalFilename())
                		.newFilename(newFilename)
                		.contentType(contentType)
                		.size(mpf.getSize())
                		.thumbnailFilename(thumbnailFile != null ? thumbnailFile.getName() : null)
                		.thumbnailSize(thumbnailFile.length())
                		.build();

                image = imageService.create(image);

                image.setUrl("/image/picture/"+image.getId());
                image.setThumbnailUrl("/image/thumbnail/"+image.getId());
                image.setDeleteUrl("/image/delete/"+image.getId());
                image.setDeleteType("DELETE");

                list.add(image);

            } catch(IOException e) {
                LOGGER.error("Could not upload file "+mpf.getOriginalFilename(), e);
            }

        }

        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        LOGGER.debug("uploaded image files: {}", files);
        return files;
    }

    /**
     * Picture.
     *
     * @param response the response
     * @param id       the id
     * @throws ImageNotFoundException the image not found exception
     */
    @RequestMapping(value = "/image/picture/{id}", method = RequestMethod.GET)
    public void picture(HttpServletResponse response, @PathVariable Long id) throws ImageNotFoundException {
        Image image = imageService.get(id);
        File imageFile = new File(fileUploadDirectory+"/"+image.getNewFilename());
        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            LOGGER.error("Could not show picture "+id, e);
        }
    }

    /**
     * Thumbnail.
     *
     * @param response the response
     * @param id       the id
     * @throws ImageNotFoundException the image not found exception
     */
    @RequestMapping(value = "/image/thumbnail/{id}", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, @PathVariable Long id) throws ImageNotFoundException {
        Image image = imageService.get(id);
        File imageFile = new File(fileUploadDirectory + "/thumbnails/" + image.getThumbnailFilename());
        response.setContentType(image.getContentType());
        response.setContentLength(image.getThumbnailSize().intValue());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            LOGGER.error("Could not show thumbnail "+id, e);
        }
    }

    /**
     * Delete list.
     *
     * @param id the id
     * @return the list
     * @throws ImageNotFoundException the image not found exception
     */
    @RequestMapping(value = "/image/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody List delete(@PathVariable Long id) throws ImageNotFoundException {
        Image image = imageService.get(id);
        imageService.delete(image);
        List<Map<String, Object>> results = new ArrayList<>();
        Map<String, Object> success = new HashMap<>();
        success.put("success", true);
        results.add(success);
        return results;
    }
}
