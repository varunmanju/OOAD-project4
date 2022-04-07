package abhi.ooad;

// I ended up using this enum a lot!
// I essentially used it instead of bothering with a name
// by printing it out in lowercase
public enum ItemType {
    PAPERSCORE ("Paper Score"), CD ("CD"), VINYL ("Vinyl"), GUITAR ("Guitar"), BASS ("Bass"), MANDOLIN ("Mandolin"), FLUTE ("Flute"), HARMONICA ("Harmonica"), CASSETTE ("Cassette"), RECORDPLAYER ("Record Player"), CDPLAYER ("CD Player"), MP3PLAYER ("MP3 Player"), CASSETTEPLAYER ("Cassette Player"), SAXOPHONE ("Saxophone"), HAT ("Hat"), SHIRT ("Shirt"), BANDANA ("Bandana"), PRACTICEAMP ("Practice Amp"), CABLE ("Cable"), STRINGS ("Strings"), GIGBAG ("Gig Bag");
    private final String name;

    ItemType(String n){
        this.name = n;
    }

    public String getName(){
        return this.name;
    }
}
