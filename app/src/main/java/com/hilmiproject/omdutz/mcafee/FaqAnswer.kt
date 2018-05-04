package com.hilmiproject.omdutz.mcafee

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_faq_answer.*

class FaqAnswer : AppCompatActivity() {
    private lateinit var answer:MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq_answer)
        var position = intent.getIntExtra("posisi",0)
        setData()
        jawaban.setText(Html.fromHtml(answer.get(position)))
    }

    public fun setData(){
        answer = mutableListOf()
        answer.add("<p><strong>Apa itu KIMS MEMBER?</strong><br /><ul>PT. Kharisma Inside Mandiri Sejahtera adalah sebuah perusahaan yang bergerak di bidang software. <br />Dan KIMS member adalah sebuah aplikasi mobile android yang memberikan keuntungan kepada seluruh reseller kami apabila telah berhasil <br />menjual produk antivirus McAfee ke end-user dan melakukan scan barcode. Keuntungan yabg didapat reseller dari proses scan barcode adalah berupa point.<br /> Dan hasil scan akan di konversikan menjadi reward yang dapat ditukarkan dengan uang atau voucher pulsa sesuai keinginan reseller (1 point senilai Rp.10.000,-).</ul></p>")
        answer.add("<p><strong>cara scan produk :</strong></p>" +
                "<ul>" +
                "<li>Untuk proses scan barcode produk, reseller harus berhasil menjual produk antivirus McAfee ke end-user dikarenakan barcode terdapat didalam produk kemasan yang masih bersegel. Barcode yang telah berhasil di scan, tidak dapat di scan kembali.</li>" +
                "</ul>" +
                "<p><strong>Berikut caara scan barcode :</strong></p>" +
                "<ul>" +
                "<li>Ambil barcode dari produk yang sudah terjual</li>" +
                "<li>Pilih fitur \"SCAN CODE\" pada aplikasi KIMS Member</li>" +
                "<li>Arahkan kamera anda pada barcode yang akan di scan</li>" +
                "<li>Dengan otomatis hasil scan barcode akan masuk ke saldo point anda</li>" +
                "</ul>")
        answer.add("<p><strong>cara claim hadiah :</strong></p>" +
                "<p>Cara untuk memperoleh hadiah dari aplikasi mobile KIMS Member, anda harus memiliki point yang didapatkan dari proses scan barcode pada produk McAfee yang berhasil anda jual. Setiap scan barcode pada produk McAfee yang telah anda jual memiliki point yang berbeda. Ketentuannya adalah 1 poin senilai Rp 10.000,-</p>" +
                "<ul>" +
                "</ul>" +
                "<p><strong>Berikut cara claim hadiah:</strong></p>" +
                "<ul>" +
                "<li>Pastikan virtual member card anda lengkap dan berstatus aktif</li>" +
                "<li>Kemudian pilih fitur \"KIMS REWARD\"</li>" +
                "<li>Lalu pilih hadiah yang tersedia pada katalog \"HADIAH\"</li>" +
                "<li>Lakukan konfirmasi - Selesai</li>" +
                "</ul>");
        answer.add("<p><strong>Apa penyebab status member tidak aktif:</strong></p>" +
                "<p>Pada aplikasi mobile android KIMS Member, anda yang terdaftar sebagai reseller kami akan memiliki virtual member card berstatus aktif dan akan secara otomatis \"non aktif\" apabila tidak ada aktivitas scan barcode produk selama 3 bulan. <br /><br /> Jika akun anda non aktif, maka anda tidak dapat mengakses seluruh fitur dari KIMS Member.</p>")
        answer.add("<p><strong>Mengaktifkan kembali virtual member saya :</strong></p>" +
                "<ul>" +
                "<li>Silahkan hubungi tim PT. KIMS untuk melakukan proses aktivasi ulang member dengan mengisi persyaratan yang telah kami tentukan.</li>" +
                "</ul>")
        answer.add("<p><strong>Apa itu McAfee:</strong></p>" +
                "<ul>" +
                "<p> <strong> McAfee </strong>Merupakan perusahaan teknologi keamanan terbesar perangkat lunak (software antivirus) di dunia yang berkantor pusat di Santa Clara, California - Amerika Serikat dan berdiri sejak tahun 1987. </ul>" +
                "\n" +
                "<ul>McAfee secara resmi telah menjadi bagian dari perusahaan Intel (si raksasa prosesor) pada tahun 2011. </ul> " +
                "\n" +
                "<ul> Seiring berkembangnya zaman dan massive-nya serangan atau kejahatan siber, McAfee tetap eksis menciptakan inovasi terbairu baik dari segi keamanan hingga segi pelayanannya.</p></ul>")
        answer.add("<p><strong>cara instalasi produk McAfee:</strong></p>" +
                "<ul>" +
                "<p>1. Buka folder installasi McAfee, pilih file install.exe kemudian jalankan</ul>" +
                "\n" +
                "<ul>2. Centang \"License Agreement\" pada aplikasi, kemudian install aplikasi McAfee anda</ul>" +
                "\n" +
                "<ul>3. Selesai installasi, pastikan device anda terkoneksi dengan internet untuk melakukan proses aktivasi product key</ul>" +
                "\n" +
                "<ul>4. Masukkan 25 digit product key yang terdapat dalam box kemasan McAfee anda</ul>" +
                "\n" +
                "<ul>5. Muncul customer subcription untuk aktivasi product key :</ul>" +
                "\n" +
                "<ul><li> Anda yang telah mendaftar dan memiliki akun McAfee, pilih \"I already have an exisiting McAfee account\" dan pilih \"I agree\" kemudian isi email dan password anda yang telah terdaftar di cloud McAfee</li></ul>" +
                "\n" +
                "<ul><li> Anda yang belum pernah mendaftar dan tidak memiliki akun McAfee, pilih \"I do not have a McAfee account\" dan pilih \"I agree\" kemudian isi email aktif anda dan buatlah password</li></ul>" +
                "\n" +
                "<ul>6. Selesai.</ul></p>")
        answer.add("<p><strong>cara top up McAfee:</strong></p>" +
                "<ul>" +
                "<ul><p><strong>TOP-UP VIA APLIKASI</ul></strong>" +
                "<ul>1. Pastikan device anda terkoneksi internet</ul>" +
                "<ul>2. Buka aplikasi McAfee, pilih menu \"Account\" klik \"Renew\"</ul>" +
                "<ul>3. Masukkan 25 digit product key yang terdapat dalam box kemasan McAfee</ul>" +
                "<ul>4. Anda akan di arahkan ke website McAfee. Isi email, kemudian close browser anda</ul>" +
                "<ul>5. Kemudian \"Verify Subscription\"</ul>" +
                "<ul>6. Selesai</ul>" +
                "\n" +
                "<ul><ul><strong>TOP-UP VIA ONLINE</strong></ul></ul>" +
                "<ul>1. Pastikan device anda terkoneksi internet</ul>" +
                "<ul>2. Kunjungi laman www.mcafee.com/kims\n" +
                "<ul>3. Masukkan 25 digit product key yang terdapat dalam box kemasan McAfee</ul>" +
                "<ul>4. Isi email, kemudian close browser anda\n</ul>" +
                "<ul>5. Kemudian \"Verify Subscription\"\n</ul>" +
                "<ul>6. Selesai</p></ul>")
        answer.add("<p><strong>cara aktivasi McAfee secara online:</strong></p>" +
                "<ul>" +
                "<p><ul>1. Pastikan device anda sudah terkoneksi dengan internet yang stabil\n</ul>" +
                "\n" +
                "<ul>2. Kunjungi laman website www.mcafee.com/kims\n</ul>" +
                "\n" +
                "<ul>3. Masukkan 25 digit product key yang terdapat dalam box kemasan McAfee anda\n</ul>" +
                "\n" +
                "<ul>4. Masukkan email dan password anda yang hendak akan didaftarkan ke cloud McAfee\n</ul>" +
                "\n" +
                "<ul>5. Klik \"INSTALL ON THIS PC\" kemudian jalankan proses installasi online dikarenakan aplikasi secara otomatis akan mengalami update database\n" +
                "\n</ul>" +
                "<ul>6. Selesai.</p></ul>")
        answer.add("<p><strong>McAfee anda trouble installasi?</strong></p>"+
                "<ul>"+
                "<p><ul>1. Kunjungi laman www.kharismaworld.co.id\n" +
                "<ul>2. Download \"McAfee Removal Tool\" dan \"McAfee Pre-Install Tool\"\n</ul>" +
                "<ul>3. Install McAfee Removal Tool, kemudian restart komputer anda\n</ul>" +
                "<ul>4. Install McAfee Pre-Install Tool dan pastikan device anda terkoneksi dengan internet\n</ul>" +
                "<ul>5. Kemudian install kembali McAfee anda\n</ul>" +
                "<ul>6. Selesai\n</ul>" +
                "\n" +
                "<ul>(NB: Apabila masih ada kendala, silahkan hubungi customer support kami)</p></ul>")
        answer.add("<p><strong>Help :</strong></p>\n" +
                "<ul>\n" +
                "<p>\n" +
                "<ul>Kami hadir dan siap membantu anda. Silahkan menuju fitur customer service yang terdapat pada aplikasi KIMS Member.\n" +
                "\n" +
                "<ul>Anda dapat menggunakan fitur chat via WhatsApp yang langsung terhubung oleh customer support kami atau kunjungi kantor support terdekat kami sesuai alamat yang tertera.</ul>"+
                "</ul>")

    }


}
