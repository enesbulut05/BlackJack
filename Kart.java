import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Kart {

	private String ceklilenKart;

	public String getCekilenKart() {
		return ceklilenKart;
	}

	public void cekilenKartiYazdir() {
		System.out.println("Çekilen Kart : " + getCekilenKart());
	}

	public void setCekilenKart(String cekilenKart) {

		this.ceklilenKart = cekilenKart;
	}

	public void kartCek() throws IOException {
		String kartlar = "kartlar.txt";
		String yeniKart = null;
		yeniKart = kartCek(kartlar);
		// setCekilenKart(n, yeniKart[n]);
		setCekilenKart(yeniKart);
	}

	private static String kartCek(String kartlar) {
		String yeniKart = null;
		StringBuilder content = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(kartlar))) {
			yeniKart = reader.readLine(); // İlk satırı oku ve "kasa" değişkenine atayın

			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(kartlar))) {
			writer.write(content.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return yeniKart;
	}

	public void desteOlustur() {
		List<String> kartlar = Arrays.asList("2", "2", "2", "2", "3", "3", "3", "3", "4", "4", "4", "4", "5", "5", "5",
				"5", "6", "6", "6", "6", "7", "7", "7", "7", "8", "8", "8", "8", "9", "9", "9", "9", "10", "10", "10",
				"10", "Q", "Q", "Q", "Q", "J", "J", "J", "J", "K", "K", "K", "K", "A", "A", "A", "A", "2", "2", "2",
				"2", "3", "3", "3", "3", "4", "4", "4", "4", "5", "5", "5", "5", "6", "6", "6", "6", "7", "7", "7", "7",
				"8", "8", "8", "8", "9", "9", "9", "9", "10", "10", "10", "10", "Q", "Q", "Q", "Q", "J", "J", "J", "J",
				"K", "K", "K", "K", "A", "A", "A", "A", "2", "2", "2", "2", "3", "3", "3", "3", "4", "4", "4", "4", "5",
				"5", "5", "5", "6", "6", "6", "6", "7", "7", "7", "7", "8", "8", "8", "8", "9", "9", "9", "9", "10",
				"10", "10", "10", "Q", "Q", "Q", "Q", "J", "J", "J", "J", "K", "K", "K", "K", "A", "A", "A", "A", "2",
				"2", "2", "2", "3", "3", "3", "3", "4", "4", "4", "4", "5", "5", "5", "5", "6", "6", "6", "6", "7", "7",
				"7", "7", "8", "8", "8", "8", "9", "9", "9", "9", "10", "10", "10", "10", "Q", "Q", "Q", "Q", "J", "J",
				"J", "J", "K", "K", "K", "K", "A", "A", "A", "A", "2", "2", "2", "2", "3", "3", "3", "3", "4", "4", "4",
				"4", "5", "5", "5", "5", "6", "6", "6", "6", "7", "7", "7", "7", "8", "8", "8", "8", "9", "9", "9", "9",
				"10", "10", "10", "10", "Q", "Q", "Q", "Q", "J", "J", "J", "J", "K", "K", "K", "K", "A", "A", "A", "A",
				"2", "2", "2", "2", "3", "3", "3", "3", "4", "4", "4", "4", "5", "5", "5", "5", "6", "6", "6", "6", "7",
				"7", "7", "7", "8", "8", "8", "8", "9", "9", "9", "9", "10", "10", "10", "10", "Q", "Q", "Q", "Q", "J",
				"J", "J", "J", "K", "K", "K", "K", "A", "A", "A", "A");

		kartlariYaz(kartlar, "kartlar.txt");
		kartlariKaristir(kartlar);
		kartlariKaristir(kartlar);
		kartlariKaristir(kartlar);
		kartlariYaz(kartlar, "kartlar.txt");
		kartlariKes();

	}

	private static void kartlariKes() {
		String dosyaYolu = "kartlar.txt";
		int satirSayisi = 156; // Silmek istediğiniz satır sayısı

		try {
			List<String> kartlar = kartlariOku(dosyaYolu);
			if (kartlar.size() > satirSayisi) {
				kartlar = kartlar.subList(satirSayisi, kartlar.size());
				kartlariYaz(kartlar, dosyaYolu);
			} else {
				System.out.println("Dosya yeterli satır içermiyor.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void kartlariKaristir(List<String> dataset) {
		Collections.shuffle(dataset);
	}

	private static List<String> kartlariOku(String dosyaYolu) throws IOException {
		List<String> kartlar = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;
			while ((satir = reader.readLine()) != null) {
				kartlar.add(satir);
			}
		}
		return kartlar;
	}

	private static void kartlariYaz(List<String> kartlar, String dosyaYolu) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {
			for (String kart : kartlar) {
				writer.write(kart);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
