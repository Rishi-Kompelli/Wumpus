import java.util.*;

public class HuntTheWumpus {

    static class Player {
        int room = 1;
    }

    static class Game {
        Player player = new Player();
        boolean playing = true;
        Scanner input = new Scanner(System.in);

        int rows;
        int cols;
        int totalRooms;
        ArrayList<ArrayList<Integer>> connections;

        Game(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.totalRooms = rows * cols;
            this.connections = new ArrayList<>();
            for (int i = 0; i <= totalRooms; i++) {
                connections.add(new ArrayList<>());
            }
            defineHexMap();
        }

        void defineHexMap() {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int room = r * cols + c + 1;
                    if (room > totalRooms) continue;

                    // Left and Right
                    if (c > 0) connections.get(room).add(room - 1);
                    if (c < cols - 1) connections.get(room).add(room + 1);

                    // Diagonals
                    if (r > 0) {
                        if (r % 2 == 0) { // even row
                            if (c > 0) connections.get(room).add(room - cols - 1); // upper-left
                            connections.get(room).add(room - cols); // upper-right
                        } else { // odd row
                            connections.get(room).add(room - cols); // upper-left
                            if (c < cols - 1) connections.get(room).add(room - cols + 1); // upper-right
                        }
                    }
                    if (r < rows - 1) {
                        if (r % 2 == 0) { // even row
                            if (c > 0) connections.get(room).add(room + cols - 1); // lower-left
                            connections.get(room).add(room + cols); // lower-right
                        } else { // odd row
                            connections.get(room).add(room + cols); // lower-left
                            if (c < cols - 1) connections.get(room).add(room + cols + 1); // lower-right
                        }
                    }
                }
            }
        }

        void printOptions() {
            System.out.print("You can move to: ");
            for (int adj : connections.get(player.room)) {
                System.out.print(adj + " ");
            }
            System.out.println();
        }

        boolean isValidMove(int from, int to) {
            return connections.get(from).contains(to);
        }

        void movePlayer(int newRoom) {
            if (newRoom >= 1 && newRoom <= totalRooms && isValidMove(player.room, newRoom)) {
                player.room = newRoom;
            } else {
                System.out.println("You can't go there!");
            }
        }

        void play() {
            while (playing) {
                System.out.println("You are in room " + player.room);
                printOptions();
                System.out.print("Pick a room to move to: ");
                int next = input.nextInt();
                movePlayer(next);
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = input.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = input.nextInt();
        Game game = new Game(rows, cols);
        game.play();
    }
}
