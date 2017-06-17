package eu.lpinto.universe.controllers;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import eu.lpinto.universe.controllers.exceptions.PermissionDeniedException;
import eu.lpinto.universe.controllers.exceptions.PreConditionException;
import eu.lpinto.universe.controllers.exceptions.UnknownIdException;
import eu.lpinto.universe.persistence.entities.Image;
import eu.lpinto.universe.persistence.entities.Organization;
import eu.lpinto.universe.persistence.facades.AbstractFacade;
import eu.lpinto.universe.persistence.facades.ImageFacade;
import eu.lpinto.universe.util.AppConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Controller for Azure storage - for images
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@Stateless
public class ImageController extends AbstractControllerCRUD<Image> {

    private static final String IMAGES_LOCAL_FOLDER = AppConstants.IMAGES_STORE_FOLDER;
    private static final String IMAGE_URL_PREFIX = AppConstants.IMAGES_URL;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
    public static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;"
                                                           + "AccountName=petuniversal;"
                                                           + "AccountKey=gQngLYMD0aWuxpkxlbDOXLDveT8ERcQ2WKQRRCeHKDniNYl5iXHQIwpk7tNvbIjBEi6o4tRh82l0Obd+Y0cu3w==";

    @EJB
    private ImageFacade facade;

    public ImageController() {
        super("Image");
    }

    /*
     * Upload
     */
    public String upload(final String filePath, final String folder) {
        switch (AppConstants.ENV) {
            case AppConstants.Enviroments.DEV: {
                return devUpload(filePath, folder);
            }
            case AppConstants.Enviroments.QA: {
                return qaUpload(filePath, folder);
            }
            case AppConstants.Enviroments.PROD: {
                return prodUpload(filePath, folder);
            }
            default:
                return Organization.DEFAULT_IMG;
        }
    }

    private String prodUpload(final String filePath, final String folder) {
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(STORAGE_CONNECTION_STRING);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            /* Container name must be lower case. */
            CloudBlobContainer container = serviceClient.getContainerReference("images");
            boolean created = container.createIfNotExists();

            /* Upload an image file. */
            File sourceFile = new File(filePath);
            CloudBlockBlob blob = container.getBlockBlobReference(folder + sourceFile.getName());

            blob.upload(new FileInputStream(sourceFile), sourceFile.length());

            String result = IMAGE_URL_PREFIX + folder + sourceFile.getName();
            return result;

        }
        catch (FileNotFoundException fileNotFoundException) {
            LOGGER.error("FileNotFoundException encountered: " + fileNotFoundException.getMessage());
            return null;
        }
        catch (StorageException storageException) {
            LOGGER.error("StorageException encountered: " + storageException.getMessage());
            return null;
        }
        catch (Exception e) {
            LOGGER.error("Exception encountered: " + e.getMessage());
            return null;
        }
    }

    private String qaUpload(final String filePath, final String folder) {
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(STORAGE_CONNECTION_STRING);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            /* Container name must be lower case. */
            CloudBlobContainer container = serviceClient.getContainerReference("images");
            boolean created = container.createIfNotExists();

            /* Upload an image file. */
            File sourceFile = new File(filePath);
            CloudBlockBlob blob = container.getBlockBlobReference("qa/" + folder + sourceFile.getName());

            blob.upload(new FileInputStream(sourceFile), sourceFile.length());

            String result = IMAGE_URL_PREFIX + folder + sourceFile.getName();
            return result;

        }
        catch (FileNotFoundException fileNotFoundException) {
            LOGGER.error("FileNotFoundException encountered: " + fileNotFoundException.getMessage());
            return null;
        }
        catch (StorageException storageException) {
            LOGGER.error("StorageException encountered: " + storageException.getMessage());
            return null;
        }
        catch (Exception e) {
            LOGGER.error("Exception encountered: " + e.getMessage());
            return null;
        }
    }

    private String devUpload(final String filePath, final String folder) {
//        String result = Base64.getEncoder().encodeToString(readContentIntoByteArray(new File(filePath)));
//        return result;

        String[] subpath = filePath.split("/");
        String aux = AppConstants.IMAGES_URL + "/" + subpath[subpath.length - 3] + "/" + subpath[subpath.length - 2] + "/" + subpath[subpath.length - 1];
        System.out.println("IMAGE URL: " + aux);
        return aux;
    }

    /*
     * Delete
     */
    @Override
    public void doDelete(final Image savedEntity) throws UnknownIdException, PermissionDeniedException, PreConditionException {
        String url = savedEntity.getUrl();

        String[] subsUrls = url.split("/");
        int length = subsUrls.length;
        String fileName = subsUrls[length - 1];
        String entityID = subsUrls[length - 2];
        String entityType = subsUrls[length - 3];

        delete(entityType + "/" + entityID + "/" + fileName);

        super.doDelete(savedEntity);
    }

    public void delete(final String filePath) {
        switch (AppConstants.ENV) {
            case AppConstants.Enviroments.DEV: {
                devDelete(filePath);
                break;
            }
            case AppConstants.Enviroments.QA: {
                qaDelete(filePath);
                break;
            }
            case AppConstants.Enviroments.PROD: {
                prodDelete(filePath);
                break;
            }
        }
    }

    private void prodDelete(final String filePath) {
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(STORAGE_CONNECTION_STRING);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            /* Container name must be lower case. */
            CloudBlobContainer container = serviceClient.getContainerReference("images");
            boolean created = container.createIfNotExists();

            /* delete an image file. */
            CloudBlockBlob blob = container.getBlockBlobReference(filePath);
            blob.delete();

        }
        catch (URISyntaxException | InvalidKeyException ex) {
        }
        catch (StorageException ex) {
            LOGGER.error("StorageException encountered: " + ex.getMessage());
        }
        catch (RuntimeException e) {
            LOGGER.error("Exception encountered: " + e.getMessage());
        }
    }

    private void qaDelete(final String filePath) {
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(STORAGE_CONNECTION_STRING);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            /* Container name must be lower case. */
            CloudBlobContainer container = serviceClient.getContainerReference("images");
            boolean created = container.createIfNotExists();

            /* delete an image file. */
            CloudBlockBlob blob = container.getBlockBlobReference("qa/" + filePath);
            blob.delete();

        }
        catch (URISyntaxException | InvalidKeyException ex) {
        }
        catch (StorageException ex) {
            LOGGER.error("StorageException encountered: " + ex.getMessage());
        }
        catch (RuntimeException e) {
            LOGGER.error("Exception encountered: " + e.getMessage());
        }
    }

    private void devDelete(final String filePath) {
        File f = new File(IMAGES_LOCAL_FOLDER + filePath);
        f.delete();
    }

//
//    private static byte[] readContentIntoByteArray(File file) {
//        FileInputStream fileInputStream = null;
//        byte[] bFile = new byte[(int) file.length()];
//        try {
//            //convert file into array of bytes
//            fileInputStream = new FileInputStream(file);
//            fileInputStream.read(bFile);
//        } catch (IOException e) {
//            return null;
//        }
//        return bFile;
//    }
    @Override
    protected AbstractFacade<Image> getFacade() {
        return facade;
    }
}
