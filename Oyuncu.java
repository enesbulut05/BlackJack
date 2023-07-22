import java.util.ArrayList;
import java.util.List;

public class Oyuncu {
	private List<String> oyuncuKartlari;
	private List<String> bolunenOyuncuKartlari2;
	private List<String> bolunenOyuncuKartlari1;
	private int oyuncuSirasi;
	private boolean oyuncu2XeBastiMi = false;
	private boolean oyuncuPasDediMi = false;
	private boolean oyuncuBoleBastiMi = false;
	private boolean oyuncununIlkTuruMu = true;
	private boolean[] bolunenSetPasaBastiMi = { false, false };
	private boolean bolunenOyuncuHepsindePasaBastiMi = false;

	public boolean isBolunenOyuncuHepsindePasaBastiMi() {
		if (bolunenSetPasaBastiMi[0] == true && bolunenSetPasaBastiMi[1] == true) {
			bolunenOyuncuHepsindePasaBastiMi = true;
		}
		return bolunenOyuncuHepsindePasaBastiMi;
	}

	public boolean[] getBolunenSetPasaBastiMi() {
		return bolunenSetPasaBastiMi;
	}

	public void setBolunenSetPasaBastiMi(boolean bolunenSetPasaBastiMi, int setSayisi) {
		this.bolunenSetPasaBastiMi[setSayisi] = bolunenSetPasaBastiMi;
	}

	public boolean isOyuncununIlkTuruMu() {
		return oyuncununIlkTuruMu;
	}

	public void setOyuncununIlkTuruMu(boolean oyuncununIlkTuruMu) {
		this.oyuncununIlkTuruMu = oyuncununIlkTuruMu;
	}

	public boolean isOyuncu2XeBastiMi() {
		return oyuncu2XeBastiMi;
	}

	public void setOyuncu2XeBastiMi(boolean oyuncu2XeBastiMi) {
		this.oyuncu2XeBastiMi = oyuncu2XeBastiMi;
	}

	public boolean isOyuncuPasDediMi() {
		return oyuncuPasDediMi;
	}

	public void setOyuncuPasDediMi(boolean oyuncuPasDediMi) {
		this.oyuncuPasDediMi = oyuncuPasDediMi;
	}

	public boolean isOyuncuBoleBastiMi() {
		return oyuncuBoleBastiMi;
	}

	public void setOyuncuBoleBastiMi(boolean oyuncuBoleBastiMi) {
		this.oyuncuBoleBastiMi = oyuncuBoleBastiMi;
	}

	public int getOyuncuSirasi() {
		return oyuncuSirasi;
	}

	public void setOyuncuSirasi(int oyuncuSirasi) {
		this.oyuncuSirasi = oyuncuSirasi;
	}

	public List<String> getBolunenOyuncuKartlari1() {
		return bolunenOyuncuKartlari1;
	}

	public void ekleBolunenOyuncuKartlari1(String eklenecekKart) {
		this.bolunenOyuncuKartlari1.add(eklenecekKart);
	}

	public List<String> getBolunenOyuncuKartlari2() {
		return bolunenOyuncuKartlari2;
	}

	public void ekleBolunenOyuncuKartlari2(String eklenecekKart) {
		this.bolunenOyuncuKartlari2.add(eklenecekKart);
	}

	private String ad;
	private Double bakiye;
	private Double bahis;

	public Double getBahis() {
		return bahis;
	}

	public void setBahis(double bahis) {
		this.bahis = bahis;
	}

	public Oyuncu(String ad, double baslangicBakiyesi, int oyuncuSirasi) {
		this.ad = ad;
		this.bakiye = baslangicBakiyesi;
		this.oyuncuSirasi = oyuncuSirasi;
		oyuncuKartlari = new ArrayList<>();
		bolunenOyuncuKartlari1 = new ArrayList<>();
		bolunenOyuncuKartlari2 = new ArrayList<>();
	}

	public String getAd() {
		return ad;
	}

	public void paraYatir(double miktar) {
		this.bakiye += miktar;
	}

	public void paraCek(double miktar) {
		if (bahis <= bakiye) {
			this.bakiye = bakiye - miktar;
		} else {
			System.out.println("Yetersiz bakiye!");
		}
	}

	public double getBakiye() {
		return this.bakiye;
	}

	public void setBakiye(double bakiye) {
		this.bakiye = bakiye;
	}

	public String oyuncuSetleriniHesapla(List<String> bolunenOyuncuKartlari) {
		String toplam = null;
		int toplam1 = 0;
		int toplam2 = 0;

		for (int i = 0; i < bolunenOyuncuKartlari.size(); i++) {
			if (bolunenOyuncuKartlari.get(i).equals("K") || bolunenOyuncuKartlari.get(i).equals("Q")
					|| bolunenOyuncuKartlari.get(i).equals("J")) {
				// setOyuncuKartlari(i, "10");
				toplam1 += 10;
				toplam2 += 10;
			} else if (bolunenOyuncuKartlari.get(i).equals("A")) {
				
				if (toplam.contains("yada")) {
					toplam1 += 1;
					toplam2 = toplam1 + 10;

				} else {
					toplam1 += 1;
					toplam2 += 11;
				}
			}
			try {
				int sayi = Integer.parseInt(bolunenOyuncuKartlari.get(i));
				toplam1 += sayi;
				toplam2 += sayi;

			} catch (Exception e) {

			}

			if (toplam1 == toplam2) {
				toplam = String.valueOf(toplam1);
			} else if (toplam1 <= 21 && toplam2 <= 21) {
				toplam = String.valueOf(toplam1) + " yada " + String.valueOf(toplam2);
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

	public String oyuncuHesapla() {
		String toplam = "";
		int toplam1 = 0;
		int toplam2 = 0;

		for (int i = 0; i < oyuncuKartlari.size(); i++) {
			if (oyuncuKartlari.get(i).equals("K") || oyuncuKartlari.get(i).equals("Q")
					|| oyuncuKartlari.get(i).equals("J")) {
				// setOyuncuKartlari(i, "10");
				toplam1 += 10;
				toplam2 += 10;
			} else if (oyuncuKartlari.get(i).equals("A")) {
				if (toplam.contains("yada")) {
					toplam1 += 1;
					toplam2 = toplam1 + 10;

				} else {
					toplam1 += 1;
					toplam2 += 11;
				}

			}
			try {
				int sayi = Integer.parseInt(oyuncuKartlari.get(i));
				toplam1 += sayi;
				toplam2 += sayi;

			} catch (Exception e) {

			}

			if (toplam1 == toplam2) {
				toplam = String.valueOf(toplam1);
			} else if (toplam1 <= 21 && toplam2 <= 21) {
				toplam = String.valueOf(toplam1) + " yada " + String.valueOf(toplam2);
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

	public List<String> getOyuncuKartlari() {
		return oyuncuKartlari;
	}

	public void ekleOyuncuKartlari(String kartEkle) {
		this.oyuncuKartlari.add(kartEkle);
	}

	public void setOyuncuKartlari(int i, String kartDegistir) {
		this.oyuncuKartlari.set(i, kartDegistir);
	}

	public void oyuncuKartlariniGoster(List<String> list) {
		if (oyuncuBoleBastiMi == false) {
			System.out.print(getAd() + " Kartlari : ");
			System.out.print(getOyuncuKartlari());
			System.out.print(" Toplam değer = " + oyuncuHesapla());
			System.out.println();
		} else {
			System.out.print(getAd() + " Kartlari : \n");
			System.out.print("1. Set : " + getBolunenOyuncuKartlari1());
			System.out.print(" Toplam değer = " + oyuncuSetleriniHesapla(bolunenOyuncuKartlari1) + "\n");

			System.out.print("2. Set : " + getBolunenOyuncuKartlari2());
			System.out.print(" Toplam değer = " + oyuncuSetleriniHesapla(bolunenOyuncuKartlari2) + "\n");
		}
	}

	public void oyuncuKartlariniGosterSon(List<String> list) {
		if (oyuncuBoleBastiMi == false) {
			System.out.print(getAd() + " Kartlari : ");

			System.out.print(getOyuncuKartlari());

			if (oyuncuHesapla().contains("yada")) {
				String sonuc = oyuncuHesapla();
				String[] parcalar = sonuc.split(" ");
				String ikinciSayi = parcalar[2];
				System.out.print(" Toplam değer = " + parcalar[2]);
			} else {
				System.out.print(" Toplam değer = " + oyuncuHesapla());
			}
			System.out.println();
		} else {
			if (oyuncuSetleriniHesapla(bolunenOyuncuKartlari1).contains("yada")) {
				String sonuc = oyuncuSetleriniHesapla(bolunenOyuncuKartlari1);
				String[] parcalar = sonuc.split(" ");
				String ikinciSayi = parcalar[2];
				System.out.println("1.Set : " + getBolunenOyuncuKartlari1() + " Toplam değer = " + parcalar[2]);
			} else {
				System.out.print(getAd() + " Kartlari : ");
				System.out.println("1.Set : " + getBolunenOyuncuKartlari1() + " Toplam değer = "
						+ oyuncuSetleriniHesapla(bolunenOyuncuKartlari1));
			}
			if (oyuncuSetleriniHesapla(bolunenOyuncuKartlari2).contains("yada")) {
				String sonuc = oyuncuSetleriniHesapla(bolunenOyuncuKartlari2);
				String[] parcalar = sonuc.split(" ");
				String ikinciSayi = parcalar[2];
				System.out.println("2.Set : " + getBolunenOyuncuKartlari2() + " Toplam değer = " + parcalar[2]);
			} else {
				System.out.print(getAd() + " Kartlari : ");
				System.out.println("2.Set : " + getBolunenOyuncuKartlari2() + " Toplam değer = "
						+ oyuncuSetleriniHesapla(bolunenOyuncuKartlari2));
			}

		}
	}

}
