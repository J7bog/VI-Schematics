package vischematics;

import arc.Core;
import arc.func.ConsT;
import arc.util.Http;
import arc.util.Http.*;

public class SQL {
    private static final String projectId = "hiacmaksrirkpwnyaeqr";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhpYWNtYWtzcmlya3B3bnlhZXFyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTM2MTU2MzgsImV4cCI6MjEwOTE5MTYzOH0.jbyFUCm4gXny6w2yaqpmRTG3MXrZucqsJHyzkzmYa30";
    
    private static final String FUNCTIONS_URL = "https://" + projectId + ".supabase.co/rest/v1/rpc/";
    
    // region functions
    
    public static void addAccount(boolean setAdmin, String name, String key, ConsT<HttpResponse, Exception> success) {
        invoke("add_account",
            success,
            '{' +
                "\"_admin_key\":\"" + Core.settings.getString("vischematics.key") + "\"," +
                "\"_set_admin\":\"" + setAdmin + "\"," +
                "\"_name\":\"" + name + "\"," +
                "\"_key\":\"" + key + "\"" +
            '}'
        );
    }
    
    public static void deleteAccount(String key, ConsT<HttpResponse, Exception> success) {
        invoke("add_account",
            success,
            '{' +
                "\"_admin_key\":\"" + Core.settings.getString("vischematics.key") + "\"," +
                "\"_key\":\"" + key + '\"' +
            '}'
        );
    }
    
    public static void setStatus(boolean isAdmin, String key, ConsT<HttpResponse, Exception> success) {
        invoke("add_account",
            success,
            '{' +
                "\"_admin_key\":\"" + Core.settings.getString("vischematics.key") + "\"," +
                "\"_is_admin\":\"" + isAdmin + "\"," +
                "\"_key\":\"" + key + '\"' +
            '}'
        );
    }
    
    public static void generateKey(ConsT<HttpResponse, Exception> success) {
        invoke("generate_key", success, "");
    }
    
    // endregion functions
    
    private static void invoke(String function, ConsT<HttpResponse, Exception> success, String body) {
        request(function, body).submit(success);
    }
    
    private static HttpRequest request(String function, String body) {
        return Http.post(FUNCTIONS_URL + function)
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .header("Content-Type", "application/json")
            .content(body);
    }
}