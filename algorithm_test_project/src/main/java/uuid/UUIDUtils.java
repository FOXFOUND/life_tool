package uuid;

import java.util.UUID;


/***
 *
 * @Project : CRM2.5
 * @Package : com.bj58.arch.apaas.service.utils
 * @Class : UUIDUtils
 * @Description : 
 * @author : LinBoWen
 * @CreateDate : 2021-07-30 17:31:25
 * @version : V1.0.0
 * @Copyright : 2021 58 Inc. All rights reserved.
 * @Reviewed : 
 * @UpateLog :    Name    Date    Reason/Contents
 *             ---------------------------------------
 *                ****    ****    **** 
 * 
 */
public class UUIDUtils {

    private UUIDUtils() {
        throw new UnsupportedOperationException("this is class can't be initialized!");
    }

    /** DEFAULT for .net's Guid default */
    public static final String DEFAULT = "00000000-0000-0000-0000-000000000000";

    /**
     * Method getUUIDString returns the UUIDString of this UUIDUtils object.
     *
     * @return the UUIDString (type String) of this UUIDUtils object.
     */
    public static String getUUIDString() {
        return UUID.randomUUID().toString();
    }
}
