package automation.wompi.Model.PagoPSE;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerdataModel {
    private String phone_number;
    private String full_name;
    private String device_id;
}
