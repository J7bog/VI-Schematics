package vischematics;

import arc.Core;
import arc.func.Cons;
import arc.util.Http;
import arc.util.Http.*;
import arc.util.Log;

public class Supabase {
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhpYWNtYWtzcmlya3B3bnlhZXFyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTM2MTU2MzgsImV4cCI6MjEwOTE5MTYzOH0.jbyFUCm4gXny6w2yaqpmRTG3MXrZucqsJHyzkzmYa30";
    private static final String FUNCTIONS_URL = "https://hiacmaksrirkpwnyaeqr.supabase.co/rest/v1/rpc/";
    
    // region functions
    
    public static void addAccount(boolean setAdmin, String name, String key, Cons<String> resultCons) {
        key(callerKey -> {
            invoke("add_account", resultCons,
                '{' +
                    "\"_caller_key\":\"" + callerKey + "\"," +
                    "\"_status\":" + setAdmin + ',' +
                    "\"_name\":\"" + name + "\"," +
                    "\"_key\":\"" + key + "\"" +
                    '}'
            );
        });
    }
    
    public static void deleteAccount(String key, Cons<String> resultCons) {
        key(callerKey -> {
            invoke("delete_account", resultCons,
                '{' +
                    "\"_caller_key\":\"" + callerKey + "\"," +
                    "\"_key\":\"" + key + '\"' +
                '}'
            );
        });
    }
    
    public static void setStatus(String status, String key, Cons<String> resultCons) {
        key(callerKey -> {
            invoke("set_status", resultCons,
                '{' +
                    "\"_caller_key\":\"" + callerKey + "\"," +
                    "\"_status\":" + status + ',' +
                    "\"_key\":\"" + key + '\"' +
                '}'
            );
        });
    }
    
    public static void generateKey(Cons<String> resultCons) {
        invoke("generate_key", key -> {
            Core.settings.put("vischematics.key", key);
            resultCons.get(key);
        }, "{" + /* empty json */ "}");
    }
    
    // endregion functions
    
    public static void key(Cons<String> keyCons) {
        String key = Core.settings.getString("vischematics.key");
        if (key == null) {
            generateKey(keyCons);
        } else {
            keyCons.get(key);
        }
    }
    
    private static void invoke(String function, Cons<String> resultCons, String json) {
        request(function, json).submit(
            res -> {
                String result = res.getResultAsString().replace("\"", "").trim();
                // 2** codes
                if (((int) (res.getStatus().code / 100)) == 2) {
                    resultCons.get(result);
                } else {
                    Log.err("SQL Error (@): @", function, result);
                    resultCons.get(null);
                }
            }
        );
    }
    
    private static HttpRequest request(String function, String json) {
        return Http.post(FUNCTIONS_URL + function)
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .header("Content-Type", "application/json")
            .content(json);
    }
}