/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public class AudioConverter {

    // Convert MultipartFile thành File tạm trên hệ thống
    public static File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("upload_", ".wav");
        multipartFile.transferTo(tempFile);
        return tempFile;
    }

    // Chuyển file WAV bất kỳ thành WAV chuẩn PCM_SIGNED 16-bit mono
    public static File convertToPcm(File sourceFile) throws IOException, UnsupportedAudioFileException {
        AudioInputStream originalStream = AudioSystem.getAudioInputStream(sourceFile);
        AudioFormat originalFormat = originalStream.getFormat();

        // Định dạng PCM mục tiêu
        AudioFormat pcmFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                originalFormat.getSampleRate(),
                16,
                1, // mono
                2, // 16-bit mono = 2 bytes per frame
                originalFormat.getSampleRate(),
                false // little endian
        );

        // Convert nếu cần
        AudioInputStream pcmStream = AudioSystem.getAudioInputStream(pcmFormat, originalStream);

        File pcmFile = File.createTempFile("pcm_", ".wav");
        AudioSystem.write(pcmStream, AudioFileFormat.Type.WAVE, pcmFile);

        // Dọn dẹp
        originalStream.close();
        pcmStream.close();

        return pcmFile;
    }

//    public static File forceConvertToPcmWithFfmpeg(File inputFile) throws IOException, InterruptedException {
//        File outputFile = File.createTempFile("converted_", ".wav");
//
//        ProcessBuilder pb = new ProcessBuilder(
//                "ffmpeg", "-y",
//                "-i", inputFile.getAbsolutePath(),
//                "-acodec", "pcm_s16le",
//                "-ar", "16000",
//                "-ac", "1",
//                outputFile.getAbsolutePath()
//        );
//
//        pb.redirectErrorStream(true);
//        Process process = pb.start();
//
//// In log ffmpeg nếu cần debug
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println("ffmpeg: " + line);
//            }
//        }
//
//        int exitCode = process.waitFor();
//        if (exitCode != 0) {
//            throw new IOException("ffmpeg exited with code: " + exitCode);
//        }
//
//        return outputFile;
//    }
}
