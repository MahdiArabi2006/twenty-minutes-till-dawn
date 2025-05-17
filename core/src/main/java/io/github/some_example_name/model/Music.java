package io.github.some_example_name.model;

public enum Music {
    BETWEEN_THE_BARS("between the bars", GameAsset.betweenTheBars),
    REQUIEM_FOR_A_DREAM("requiem for a dream", GameAsset.requiemForADream),
    HEATHENS("heathens", GameAsset.heathens);

    private final String name;
    private final com.badlogic.gdx.audio.Music music;

    Music(String name, com.badlogic.gdx.audio.Music music) {
        this.name = name;
        this.music = music;
    }

    public String getName() {
        return name;
    }

    public void play(){
        this.music.setLooping(true);
        this.music.setVolume(1f);
        this.music.play();
    }

    public void pause(){
        this.music.pause();
    }

    public void changeVolume(float volume){
        this.music.setVolume(volume);
    }
}
