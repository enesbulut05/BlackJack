import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DefaultEditorKit.CopyAction;

public class Kasa {

	private List<String> kasaKartlari;

	public Kasa() {
		kasaKartlari = new ArrayList<>();
	}

	public List<String> getKasaKartlari() {
		return kasaKartlari;
	}

	public void ekleKasaKartlari(String kartEkle) {
		this.kasaKartlari.add(kartEkle);
	}

	public void setKasaKartlari(int i, String kartDegistir) {
		this.kasaKartlari.set(i, kartDegistir);
	}

	public void kasaOynat() throws IOException {
		Kart kart = new Kart();
		boolean as = false;
		if (kasaHesapla().contains("yada")) {
			as = true;
		}
		if (as == false) {
			while (true) {
				if (Integer.parseInt(kasaHesapla()) < 17) {
					kart.kartCek();
					ekleKasaKartlari(kart.getCekilenKart());
					System.out.println("Çekilen Kart : " + kart.getCekilenKart());
				} else
					break;
			}

		} else if (as == true) {
			int birinci, ikinci;
			String[] sayilar = kasaHesapla().split(" ");
			birinci = Integer.parseInt(sayilar[0]);
			ikinci = Integer.parseInt(sayilar[2]);
			while (true) {
				if (birinci < 17 && ikinci < 17) {
					kart.kartCek();
					ekleKasaKartlari(kart.getCekilenKart());
					System.out.println("Çekilen Kart : " + kart.getCekilenKart());
				} else
					break;
			}
		}
	}

	public String kasaHesapla() {
		String toplam = null;
		int toplam1 = 0;
		int toplam2 = 0;
	
		for (int i = 0; i < kasaKartlari.size(); i++) {
			if (kasaKartlari.get(i).equals("K") || kasaKartlari.get(i).equals("Q") || kasaKartlari.get(i).equals("J")) {
				// setKasaKartlari(i, "10");
				toplam1 += 10;
				toplam2 += 10;
			} else if (kasaKartlari.get(i).equals("A")) {

				toplam1 += 1;
				toplam2 += 11;

			}
			try {
				int sayi = Integer.parseInt(kasaKartlari.get(i));
				toplam1 += sayi;
				toplam2 += sayi;

			} catch (Exception e) {

			}

			if (toplam1 == toplam2) {
				toplam = String.valueOf(toplam1);
			} else if (toplam1 <= 21 && toplam2 <= 21) {

				if (toplam1 < toplam2)
					toplam = String.valueOf(toplam2);
				else if (toplam1 > toplam2)
					toplam = String.valueOf(toplam1);

			} else if (toplam1 <= 21 && toplam2 > 21) {
				toplam = String.valueOf(toplam1);
			} else if (toplam2 <= 21 && toplam1 > 21) {
				toplam = String.valueOf(toplam2);
			} else if (toplam1 > 21 && toplam2 > 21) {

				if (toplam1 < toplam2)
					toplam = String.valueOf(toplam1);
				else if (toplam1 > toplam2)
					toplam = String.valueOf(toplam2);
			}
		}
		return toplam;
	}

	public void kasaKartlariniGosterGizli(List<String> list) {
		System.out.print("Kasa Kartlari : ");

		System.out.print("["+list.get(0) + ", *]");

		System.out.println();
	}

	public void kasaKartlariniGoster(List<String> list) {
		System.out.print("Kasa Kartlari : ");
		
		System.out.print(getKasaKartlari());
		System.out.print(" Toplam değer = " + kasaHesapla());
		System.out.println();
	}

}
