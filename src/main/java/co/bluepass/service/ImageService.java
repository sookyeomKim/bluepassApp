/**
 *
 */
package co.bluepass.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.bluepass.domain.Image;
import co.bluepass.exception.ImageNotFoundException;
import co.bluepass.repository.ImageRepository;

/**
 * The type Image service.
 */
@Service
public class ImageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

	@Inject
	private Environment env;

	private final ImageRepository repository;

	private String fileUploadDirectory;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        this.fileUploadDirectory = env.getProperty("file.upload.directory");
    }

    /**
     * Instantiates a new Image service.
     *
     * @param repository the repository
     */
    @Autowired
	public ImageService(ImageRepository repository) {
		this.repository = repository;
	}

    /**
     * List list.
     *
     * @return the list
     */
    public List<Image> list() {
		LOGGER.debug("Image list");
		return repository.findAll();
	}

    /**
     * Create image.
     *
     * @param image the image
     * @return the image
     */
    @Transactional

	public Image create(Image image) {
		LOGGER.debug("Add Image entry with information: {}", image);
		repository.save(image);
		return image;
	}


    /**
     * Get image.
     *
     * @param id the id
     * @return the image
     * @throws ImageNotFoundException the image not found exception
     */
    public Image get(Long id) throws ImageNotFoundException {
		LOGGER.debug("Find by id: {}", id);

		Image found = repository.findOne(id);
		LOGGER.debug("Found Image entry: {}", found);

		if(found == null){
			throw new ImageNotFoundException("ID {}번 이미지를 찾을 수 없습니다.");
		}

		return found;
	}


    /**
     * Delete image.
     *
     * @param image the image
     * @return the image
     * @throws ImageNotFoundException the image not found exception
     */
    public Image delete(Image image) throws ImageNotFoundException {
		LOGGER.debug("Deleting image {}", image);

        File imageFile = new File(fileUploadDirectory+"/"+image.getNewFilename());
        imageFile.delete();
        File thumbnailFile = new File(fileUploadDirectory+"/"+image.getThumbnailFilename());
        thumbnailFile.delete();

		repository.delete(image);

		return image;
	}


    /**
     * Delete by id image.
     *
     * @param id the id
     * @return the image
     * @throws ImageNotFoundException the image not found exception
     */
    public Image deleteById(Long id) throws ImageNotFoundException {
		LOGGER.debug("Deleting image {}", id);

		Image deleted = repository.findOne(id);
		LOGGER.debug("Found Image entry: {}", deleted);

		if(deleted == null){
			throw new ImageNotFoundException("ID {}번 이미지를 찾을 수 없습니다.");
		}

		repository.delete(deleted);

		return deleted;
	}


    /**
     * Create image.
     *
     * @param file the file
     * @return the image
     */
    @Transactional

	public Image create(MultipartFile file) {
		Image image = null;

        LOGGER.debug("Uploading {}", file.getOriginalFilename());
        String newFilenameBase = UUID.randomUUID().toString();
        String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFilename = newFilenameBase + originalFileExtension;
        String storageDirectory = fileUploadDirectory;
        String contentType = file.getContentType();

        File newFile = new File(storageDirectory + "/" + newFilename);
        File thumbnailFile = null;
        try {
            file.transferTo(newFile);

            thumbnailFile = makeThumbnailFile(newFile);

            image = Image.getBuilder(file.getOriginalFilename())
            		.newFilename(newFilename)
            		.contentType(contentType)
            		.size(file.getSize())
            		.thumbnailFilename(thumbnailFile != null ? thumbnailFile.getName() : null)
            		.thumbnailSize(thumbnailFile != null ? thumbnailFile.length() : 0)
            		.build();

            image = create(image);

            image.setUrl("/image/picture/"+image.getId());
            image.setThumbnailUrl("/image/thumbnail/"+image.getId());
            image.setDeleteUrl("/image/delete/"+image.getId());
            image.setDeleteType("DELETE");

        } catch(IOException e) {
            LOGGER.error("Could not upload file "+file.getOriginalFilename(), e);
        } finally {
        	thumbnailFile = null;
        }

		return image;
	}


    /**
     * Make thumbnail file file.
     *
     * @param file the file
     * @return the file
     */
    public File makeThumbnailFile(File file) {
		File thumbnailFile = null;
		try {
			String newFilenameBase = UUID.randomUUID().toString();
			//String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String originalFileExtension = file.getName().substring(file.getName().lastIndexOf("."));
			String storageDirectory = fileUploadDirectory + "/thumbnails";

			BufferedImage thumbnail = Scalr.resize(ImageIO.read(file), 290);

			String thumbnailFilename = newFilenameBase + "-thumbnail.png";
			thumbnailFile = new File(storageDirectory + "/" + thumbnailFilename);
			ImageIO.write(thumbnail, "png", thumbnailFile);
		} catch (Throwable th) {
			th.printStackTrace();
			thumbnailFile = null;
		}

		return thumbnailFile;
	}


	private void writeImage(String fileName, BufferedImage croped) throws IOException {
		File thumbnailFile;
		String storageDirectory = fileUploadDirectory + "/thumbnails";
		thumbnailFile = new File(storageDirectory + "/" + fileName);
		ImageIO.write(croped, "png", thumbnailFile);
	}

    /**
     * Find by id in list.
     *
     * @param imagesIds the images ids
     * @return the list
     */
    public List<Image> findByIdIn(Long[] imagesIds) {
		List<Long> ids = Arrays.asList(imagesIds);
		List<Image> images = repository.findAll(ids);
		return images;
	}

    /**
     * Find by id image.
     *
     * @param imageId the image id
     * @return the image
     */
    public Image findById(Long imageId) {
		return repository.findOne(imageId);
	}

}
