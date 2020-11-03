package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialList {
    @SerializedName("material")
    private List<Material> materials;

    public List<Material> getMaterials() {
        return materials;
    }
}
