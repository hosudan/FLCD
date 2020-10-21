import Model.HHashTable;

public class main {
    public static void main(String[] args){
        HHashTable<Integer, String> map = new HHashTable();
        map.put (512, "pizza");
        map.put (2, "dwadwadawdadsdz");
        map.put (510, "junge");
        System.out.println(map.get(2));
        System.out.println(map.get(510));
        map.remove(510);
        System.out.println(map.get(510));
    }
}
