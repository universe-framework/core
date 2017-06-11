package eu.lpinto.universe.util;

/**
 * Constants related with avatars.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public final class AvatarConstants {

    public static final String AVATAR_FOLDER = AppConstants.IMAGES_STORE_FOLDER;
    public static final String AVATAR_FILE_NAME = "/logo.jpg";
    public static final String AVATAR_URL_PREFIX = AppConstants.IMAGES_URL;

    private AvatarConstants() {
        throw new AssertionError("Private Constructor.");
    }
}
