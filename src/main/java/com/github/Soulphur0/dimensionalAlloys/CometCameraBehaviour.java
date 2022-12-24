package com.github.Soulphur0.dimensionalAlloys;

import com.github.Soulphur0.dimensionalAlloys.client.render.CometCameraSubmersionType;

public interface CometCameraBehaviour {

    default CometCameraSubmersionType comet_getSubmersionType() {
        return null;
    }
}
