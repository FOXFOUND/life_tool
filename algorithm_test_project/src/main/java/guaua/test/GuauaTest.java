package guaua.test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuauaTest {

    public static final int cacheTime = 60;

    public static void main(String[] args) throws ExecutionException {
        LoadingCache<String, Object> loadingCache = CacheBuilder
                .newBuilder()
                //   .expireAfterWrite(1, TimeUnit.MINUTES)
                .expireAfterWrite(cacheTime, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Object>() {

                    /**
                     * Computes or retrieves the value corresponding to {@code key}.
                     *
                     * @param key the non-null key whose value should be loaded
                     * @return the value associated with {@code key}; <b>must not be null</b>
                     * @throws Exception            if unable to load the result
                     * @throws InterruptedException if this method is interrupted. {@code InterruptedException} is
                     *                              treated like any other {@code Exception} in all respects except that, when it is caught,
                     *                              the thread's interrupt status is set
                     */
                    @Override
                    public Object load(String key) throws Exception {
                        System.out.println("进入 " + key);
                        return "1";
                    }
                });

        System.out.println(loadingCache.get("a"));
        System.out.println(loadingCache.get("a"));

        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        loadingCache.invalidateAll(stringList);
        System.out.println(loadingCache.get("a"));
    }
}
