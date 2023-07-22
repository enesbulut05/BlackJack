import java.io.IOException;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.security.auth.DestroyFailedException;

public class Main {

	static Kart kart = new Kart();

	public static void main(String[] args) throws IOException {
		double bakiyeMiktari[] = { 500, 50, 500, 500, 500, 500, 500 }; // burasi array olmalı. Başlangıç aynı olabilir
		// // sonra değişecek.
		double bahis = 50;

		boolean yeni = true; // yeni oyuna basıldığında yeni = true olup iç döngüden çıkacak.
		while (yeni == true) {
			yeni = false;

			kart.desteOlustur();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Oyuncu sayısını (en fazla 7) girin: ");
			int oyuncuSayisi = scanner.nextInt();
			if (oyuncuSayisi > 7) {
				while (true) {
					System.out.println("Yanlış giriş. Oyuncu sayısını (en fazla 7) girin: ");
					oyuncuSayisi = scanner.nextInt();
					if (oyuncuSayisi <= 7)
						break;
				}
			}
			String fakeString = scanner.nextLine();
			String[] oyuncuAdlari = new String[7];

			for (int i = 0; i < oyuncuSayisi; i++) {
				System.out.println("Lütfen " + (i + 1) + ". Oyuncunun adını girin.");
				oyuncuAdlari[i] = scanner.nextLine();
			}

			while (true) {
				Kasa kasa = new Kasa();
				List<Oyuncu> oyuncular = new ArrayList<>();
				for (int i = 0; i < oyuncuSayisi; i++) {
					if (bakiyeMiktari[i] >= bahis) {
						Oyuncu oyuncu = new Oyuncu(oyuncuAdlari[i], bakiyeMiktari[i], (i + 1));
						oyuncu.setBahis(bahis);
						oyuncular.add(oyuncu);
					} else {
						System.out.println(oyuncuAdlari[i] + " Yetersiz Bakiye Sebeyile Oyuna Dahil Edilmedi.");
					}
				}

				// Oluşturulan oyuncuların isimlerini yazdır.

				for (Oyuncu oyuncu : oyuncular) {
					System.out.println(oyuncu.getAd() + " Bakiye : " + oyuncu.getBakiye());
				}

				// Kart dağıt
				for (int i = 0; i < 2; i++) {
					for (Oyuncu oyuncu : oyuncular) {

						if (i == 1) {
							oyuncu.paraCek((oyuncu.getBahis()));
						}
						kart.kartCek();
						oyuncu.ekleOyuncuKartlari(kart.getCekilenKart());

					}
					kart.kartCek();
					kasa.ekleKasaKartlari(kart.getCekilenKart());
				}

				// Oyuncuların kartlarını ve kasadaki kartları gösterme
				for (Oyuncu oyuncu : oyuncular) {
					oyuncu.oyuncuKartlariniGoster(oyuncu.getOyuncuKartlari());
				}
				kasa.kasaKartlariniGosterGizli(kasa.getKasaKartlari());

				// Oyunculara tek tek soru sor.
				boolean herkesPasaBastiMi = false;
				boolean pasKontrol[] = new boolean[oyuncuSayisi];

				while (herkesPasaBastiMi == false) {
					for (Oyuncu oyuncu : oyuncular) {

						if (oyuncu.isBolunenOyuncuHepsindePasaBastiMi() == true) {
							oyuncu.setOyuncuPasDediMi(true);
						}
						if (oyuncu.isOyuncuPasDediMi() == false) {

							if (oyuncu.isOyuncununIlkTuruMu() == true) {

								oyuncuyaSoruSorIlk(oyuncu);
								oyuncu.setOyuncununIlkTuruMu(false);
							} else {
								oyuncuyaSoruSor(oyuncu);
							}
						}
						int pasSayaci = 0;
						pasKontrol[oyuncu.getOyuncuSirasi() - 1] = oyuncu.isOyuncuPasDediMi();
						for (int i = 0; i < oyuncuSayisi; i++) {

							if (pasKontrol[i] == true) {
								pasSayaci++;
								if (pasSayaci == oyuncular.size())
									herkesPasaBastiMi = true;
							}
						}
					}
				}

				// Oyuncuların ve Kasanın Kartlarını Göster
				for (Oyuncu oyuncu : oyuncular) {
					oyuncu.oyuncuKartlariniGosterSon(oyuncu.getOyuncuKartlari());
				}

				System.out.println("Kasa Oynuyor...");
				kasa.kasaOynat();
				kasa.kasaKartlariniGoster(kasa.getKasaKartlari());

				// KAZANAN KAYBEDEN

				int z = 0;
				for (Oyuncu oyuncu : oyuncular) {
					if (oyuncu.isOyuncuBoleBastiMi() == true) {
						boleBasanSonucu(oyuncu, kasa);
					} else {
						normalOyuncuSonucu(oyuncu, kasa);
					}
					bakiyeMiktari[z] = oyuncu.getBakiye();
					z++;
				}

				// Tur Bitti.
				System.out.println(
						"Çıkmak için 'kapat' yazın. Devam etmek için herhangi bir tuşa basın. Yeni deste için 'deste' yazın. Yeni Oyun için yeni yazın.");
				String secim = scanner.nextLine();
				if (secim.equals("KAPAT") || secim.equals("kapat")) {
					System.out.println("Güle Güle :)");
					yeni = false;
					break;
				} else if (secim.equals("deste") || secim.equals("DESTE")) {
					kart.desteOlustur();
					System.out.println("Yeni deste yüklendi...");
				} else if (secim.equals("yeni") || secim.equals("YENİ")) {
					yeni = true;
					break;
				}

			}
		}
	}

	public static void normalOyuncuSonucu(Oyuncu oyuncu, Kasa kasa) {
		int skorOyuncu = 0;
		if (oyuncu.oyuncuHesapla().contains("yada")) {
			String sonuc = oyuncu.oyuncuHesapla();
			String[] parcalar = sonuc.split(" ");
			String ikinciSayi = parcalar[2];
			skorOyuncu = Integer.parseInt(ikinciSayi);
		} else {
			skorOyuncu = Integer.parseInt(oyuncu.oyuncuHesapla());
		}

		int skorKasa = Integer.parseInt(kasa.kasaHesapla());
		if ((skorOyuncu <= 21 && skorOyuncu > skorKasa) || (skorOyuncu <= 21 && skorKasa > 21)) {
			oyuncu.paraYatir((oyuncu.getBahis() * 2));
			System.out.println(oyuncu.getAd() + " KAZANDI. Bakiye Miktari : " + oyuncu.getBakiye());
		}

		else if (skorOyuncu > 21 || (skorOyuncu < skorKasa && skorKasa <= 21)) {

			System.out.println(oyuncu.getAd() + " KAYBETTİ. Bakiye Miktari : " + oyuncu.getBakiye());
		}

		else if (skorOyuncu == skorKasa && skorOyuncu <= 21) {
			oyuncu.paraYatir(oyuncu.getBahis());
			System.out.println(oyuncu.getAd() + " BERABERE. Bakiye Miktari : " + oyuncu.getBakiye());

		}

	}

	public static void boleBasanSonucu(Oyuncu oyuncu, Kasa kasa) {
		int skorOyuncu1 = 0;
		int skorOyuncu2 = 0;
		int skorKasa = Integer.parseInt(kasa.kasaHesapla());

		if (oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari1()).contains("yada")) {
			String sonuc = oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari1());
			String[] parcalar = sonuc.split(" ");
			String ikinciSayi = parcalar[2];
			skorOyuncu1 = Integer.parseInt(ikinciSayi);
		} else {
			skorOyuncu1 = Integer.parseInt(oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari1()));
		}

		if ((skorOyuncu1 <= 21 && skorOyuncu1 > skorKasa) || (skorOyuncu1 <= 21 && skorKasa > 21)) {
			oyuncu.paraYatir((oyuncu.getBahis() * 2));
			System.out.println(oyuncu.getAd() + "- 1.SET KAZANDI.");
		}

		else if (skorOyuncu1 > 21 || (skorOyuncu1 < skorKasa && skorKasa <= 21)) {

			System.out.println(oyuncu.getAd() + "- 1.SET KAYBETTİ.");
		}

		else if (skorOyuncu1 == skorKasa && skorOyuncu1 <= 21) {
			oyuncu.paraYatir(oyuncu.getBahis());
			System.out.println(oyuncu.getAd() + "- 1.SET BERABERE.");

		}

		if (oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari2()).contains("yada")) {
			String sonuc = oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari2());
			String[] parcalar = sonuc.split(" ");
			String ikinciSayi = parcalar[2];
			skorOyuncu2 = Integer.parseInt(ikinciSayi);
		} else {
			skorOyuncu2 = Integer.parseInt(oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari2()));
		}

		if ((skorOyuncu2 <= 21 && skorOyuncu2 > skorKasa) || (skorOyuncu2 <= 21 && skorKasa > 21)) {
			oyuncu.paraYatir((oyuncu.getBahis() * 2));
			System.out.println(oyuncu.getAd() + "- 2.SET KAZANDI.");
		}

		else if (skorOyuncu2 > 21 || (skorOyuncu2 < skorKasa && skorKasa <= 21)) {

			System.out.println(oyuncu.getAd() + "- 2.SET KAYBETTİ.");
		}

		else if (skorOyuncu2 == skorKasa && skorOyuncu2 <= 21) {
			oyuncu.paraYatir(oyuncu.getBahis());
			System.out.println(oyuncu.getAd() + "- 2.SET BERABERE.");

		}
		System.out.println(oyuncu.getAd() + " Bakiye Miktari : " + oyuncu.getBakiye());
	}

	public static void oyuncuyaSoruSorIlk(Oyuncu oyuncu) throws IOException {
		Scanner scanner = new Scanner(System.in);
		oyuncu.oyuncuKartlariniGoster(oyuncu.getOyuncuKartlari());
		String secim;

		if (oyuncu.getOyuncuKartlari().get(0).equals(oyuncu.getOyuncuKartlari().get(1))) {
			System.out.println(oyuncu.getAd() + ": Lütfen Seçiniz -> PAS / KART / 2x (Bahsi İkiye Katlar)/ Böl");
			secim = scanner.nextLine();
			while (!secim.equals("pas") && !secim.equals("PAS") && !secim.equals("kart") && !secim.equals("KART")
					&& !secim.equals("2x") && !secim.equals("2X") && !secim.equals("Böl") && !secim.equals("BÖL")
					&& !secim.equals("Bol") && !secim.equals("BOL") && !secim.equals("böl") && !secim.equals("bol")) {
				System.out.println("Yanlış giris yaptınız.");
				System.out.println(oyuncu.getAd() + ": Lütfen Seçiniz -> PAS / KART / 2x (Bahsi İkiye Katlar)/ Böl");
				secim = scanner.nextLine();
			}
		} else {
			System.out.println(oyuncu.getAd() + ": Lütfen Seçiniz -> PAS / KART / 2x (Bahsi İkiye Katlar)");
			secim = scanner.nextLine();
			while (!secim.equals("pas") && !secim.equals("PAS") && !secim.equals("kart") && !secim.equals("KART")
					&& !secim.equals("2x") && !secim.equals("2X")) {
				System.out.println("Yanlış giris yaptınız.");
				System.out.println(oyuncu.getAd() + ": Lütfen Seçiniz -> PAS / KART / 2x (Bahsi İkiye Katlar)");
				secim = scanner.nextLine();
			}
		}

		if (secim.equals("2x") || secim.equals("2X")) {
			if (oyuncu.getBakiye() < oyuncu.getBahis()) {
				System.out.println("Bakiyeniz 2x Bahis İçin Yetesiz. \nLütfen seçiniz. PAS / KART ");
			} else {
				oyuncu.setOyuncu2XeBastiMi(true);
				oyuncu.paraCek(oyuncu.getBahis());
				oyuncu.setBahis(oyuncu.getBahis() * 2);
				if (oyuncu.getOyuncuKartlari().get(0).equals(oyuncu.getOyuncuKartlari().get(1))) {
					System.out.println(oyuncu.getAd() + " Bahis 2ye katlandı. Lütfen seçiniz. PAS / KART / Böl");
				} else {
					System.out.println(oyuncu.getAd() + " Bahis 2ye katlandı. Lütfen seçiniz. PAS / KART ");
				}
			}
			secim = scanner.nextLine();

		}
		if ((secim.equals("böl") || secim.equals("bol") || secim.equals("BOL") || secim.equals("BÖL")
				|| secim.equals("Bol") || secim.equals("Böl"))) {
			if (oyuncu.getBakiye() < oyuncu.getBahis()) {

				System.out.println("Bakiyeniz Kartları Bölmek İçin Yetesiz. \nLütfen seçiniz. PAS / KART ");
				secim = scanner.nextLine();
			} else {
				oyuncu.setOyuncuBoleBastiMi(true);
				oyuncu.paraCek(oyuncu.getBahis());
				kart.kartCek();
				oyuncu.ekleOyuncuKartlari(kart.getCekilenKart());// index 2
				kart.kartCek();
				oyuncu.ekleOyuncuKartlari(kart.getCekilenKart());// index 3
				// oyuncukarları[0,2] 1. set oyuncukarları[1,3] 2. set
				oyuncu.ekleBolunenOyuncuKartlari1(oyuncu.getOyuncuKartlari().get(0));
				oyuncu.ekleBolunenOyuncuKartlari1(oyuncu.getOyuncuKartlari().get(2));
				oyuncu.ekleBolunenOyuncuKartlari2(oyuncu.getOyuncuKartlari().get(1));
				oyuncu.ekleBolunenOyuncuKartlari2(oyuncu.getOyuncuKartlari().get(3));
				System.out.println("Kartlar Bölündü Yeni Kartlarınız: \nSet 1 : " + oyuncu.getBolunenOyuncuKartlari1()
						+ " Toplam Değer : " + oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari1()));
				System.out.println("Set 2 : " + oyuncu.getBolunenOyuncuKartlari2() + " Toplam Değer : "
						+ oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari2()));
				for (int i = 1; i <= 2; i++) {
					System.out.println(i + ". setiniz için seçin : PAS / KART");
					String secim2 = scanner.nextLine();
					if (secim2.equals("pas") || secim2.equals("PAS")) {
						oyuncu.setBolunenSetPasaBastiMi(true, (i - 1));
					} else if (secim2.equals("kart") || secim2.equals("KART")) {
						kart.kartCek();
						kart.cekilenKartiYazdir();
						if (i == 1) {
							oyuncu.ekleBolunenOyuncuKartlari1(kart.getCekilenKart());
							System.out.print("1. Set : " + oyuncu.getBolunenOyuncuKartlari1());
							System.out.print(" Toplam Değer : "
									+ oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari1()) + "\n");
						}
						if (i == 2) {
							oyuncu.ekleBolunenOyuncuKartlari2(kart.getCekilenKart());
							System.out.print("2. Set  : " + oyuncu.getBolunenOyuncuKartlari2());
							System.out.print(" Toplam Değer : "
									+ oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari2()) + "\n");
						}
					}
					if (i == 1) {
						try {
							if (Integer
									.parseInt(oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari1())) > 21) {
								System.out.print(" PATLADIN :) \n");
								oyuncu.setBolunenSetPasaBastiMi(true, i - 1);

							}
						} catch (Exception e) {

						}
					}
					if (i == 2) {
						try {
							if (Integer
									.parseInt(oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari2())) > 21) {
								System.out.print(" PATLADIN :) \n");
								oyuncu.setBolunenSetPasaBastiMi(true, i - 1);

							}
						} catch (Exception e) {

						}
					}
				}
			}
		}

		if (secim.equals("kart") || secim.equals("KART")) {
			kart.kartCek();
			kart.cekilenKartiYazdir();
			oyuncu.ekleOyuncuKartlari(kart.getCekilenKart());
			oyuncu.oyuncuKartlariniGoster(oyuncu.getOyuncuKartlari());
		} else if (secim.equals("pas") || secim.equals("PAS")) {
			oyuncu.setOyuncuPasDediMi(true); //
		}
		try {
			if (Integer.parseInt(oyuncu.oyuncuHesapla()) > 21 && (oyuncu.isOyuncuBoleBastiMi() == false)) {
				System.out.print(" PATLADIN :) \n");
				oyuncu.setOyuncuPasDediMi(true);

			}
		} catch (Exception e) {
			String test = oyuncu.oyuncuHesapla();
			String[] parcalar = test.split(" ");
			if (Integer.parseInt(parcalar[0]) > 21 && Integer.parseInt(parcalar[2]) > 21
					&& (oyuncu.isOyuncuBoleBastiMi() == false)) {
				System.out.print(" PATLADIN :) \n");
				oyuncu.setOyuncuPasDediMi(true);

			}
		}
	}

	public static void oyuncuyaSoruSor(Oyuncu oyuncu) throws IOException {
		Scanner scanner = new Scanner(System.in);

		// ?? Gereksiz ama zararı yok
		try {
			if (Integer.parseInt(oyuncu.oyuncuHesapla()) > 21) {
				oyuncu.setOyuncuPasDediMi(true);
			}
		} catch (Exception e) {

		}

		oyuncu.oyuncuKartlariniGoster(oyuncu.getOyuncuKartlari());

		if (oyuncu.isOyuncuBoleBastiMi() == true) {
			for (int i = 1; i <= 2; i++) {

				if (oyuncu.getBolunenSetPasaBastiMi()[i - 1] == false) {
					System.out.println(oyuncu.getAd() + " : " + i + ". setiniz için seçin : PAS/KART");
					String secim2 = scanner.nextLine();

					while (!secim2.equals("pas") && !secim2.equals("PAS") && !secim2.equals("kart")
							&& !secim2.equals("KART")) {
						System.out.println("Yanlış giris yaptınız.");
						System.out.println(oyuncu.getAd() + ": " + i + ". setiniz için seçin : PAS/KART");
						secim2 = scanner.nextLine();
					}

					if (secim2.equals("pas") || secim2.equals("PAS")) {
						oyuncu.setBolunenSetPasaBastiMi(true, (i - 1));
						break;
					} else if (secim2.equals("kart") || secim2.equals("KART")) {
						kart.kartCek();
						kart.cekilenKartiYazdir();
						if (i == 1) {
							oyuncu.ekleBolunenOyuncuKartlari1(kart.getCekilenKart());
						}
						if (i == 2) {
							oyuncu.ekleBolunenOyuncuKartlari2(kart.getCekilenKart());
						}
					}

					if (i == 1) {
						try {
							if (Integer
									.parseInt(oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari1())) > 21) {
								System.out.print(" PATLADIN :) \n");
								oyuncu.setBolunenSetPasaBastiMi(true, i - 1);

							}
						} catch (Exception e) {

						}
					}
					if (i == 2) {
						try {
							if (Integer
									.parseInt(oyuncu.oyuncuSetleriniHesapla(oyuncu.getBolunenOyuncuKartlari2())) > 21) {
								System.out.print(" PATLADIN :) \n");
								oyuncu.setBolunenSetPasaBastiMi(true, i - 1);

							}
						} catch (Exception e) {

						}
					}
				}
			}
		}

		else {

			System.out.println(oyuncu.getAd() + ": Lütfen Seçiniz -> PAS / KART");
			String secim2 = scanner.nextLine();
			while (!secim2.equals("pas") && !secim2.equals("PAS") && !secim2.equals("kart") && !secim2.equals("KART")
					&& !secim2.equals("2x") && !secim2.equals("2X") && !secim2.equals("Böl") && !secim2.equals("BÖL")
					&& !secim2.equals("Bol") && !secim2.equals("BOL")) {
				System.out.println("Yanlış giris yaptınız.");
				System.out.println(oyuncu.getAd() + ": Lütfen Seçiniz -> PAS / KART");
				secim2 = scanner.nextLine();
			}

			if (secim2.equals("kart") || secim2.equals("KART")) {
				kart.kartCek();
				kart.cekilenKartiYazdir();
				oyuncu.ekleOyuncuKartlari(kart.getCekilenKart());
				oyuncu.oyuncuKartlariniGoster(oyuncu.getOyuncuKartlari());
			} else if (secim2.equals("pas") || secim2.equals("PAS")) {
				oyuncu.setOyuncuPasDediMi(true);
			}
			try {
				if (Integer.parseInt(oyuncu.oyuncuHesapla()) > 21 && (oyuncu.isOyuncuBoleBastiMi() == false)) {
					System.out.print(" PATLADIN :) \n");
					oyuncu.setOyuncuPasDediMi(true);
				}
			} catch (Exception e) {

			}
		}

	}

}
