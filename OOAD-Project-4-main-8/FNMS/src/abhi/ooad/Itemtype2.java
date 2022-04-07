package abhi.ooad;

public enum Itemtype2 {
	
	    GUITAR ("Guitar"), BASS ("Bass"), MANDOLIN ("Mandolin"), FLUTE ("Flute"), HARMONICA ("Harmonica"), CASSETTE ("Cassette"), RECORDPLAYER ("Record Player"), CDPLAYER ("CD Player"), MP3PLAYER ("MP3 Player"), CASSETTEPLAYER ("Cassette Player"), SAXOPHONE ("Saxophone");
	    private final String name;

	    Itemtype2(String n){
	        this.name = n;
	    }

	    public String getName(){
	        return this.name;
	    }
	}


