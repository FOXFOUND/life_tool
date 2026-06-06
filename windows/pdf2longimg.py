#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
PDF 一键转高清长图脚本（清晰度拉满版）
依赖：pymupdf, pillow
安装：pip install pymupdf pillow
"""

import fitz
from PIL import Image
import sys
import os

def pdf_to_long_image(pdf_path, output_path="长图.png"):
    if not os.path.exists(pdf_path):
        print(f"错误：文件 {pdf_path} 不存在！")
        return

    try:
        doc = fitz.open(pdf_path)
        print(f"成功打开PDF，共 {len(doc)} 页")

        img_list = []
        # ====================== 高清核心代码 ======================
        ZOOM = 1  # 缩放倍数：1=普通，2=高清，3=超清
        matrix = fitz.Matrix(ZOOM, ZOOM)  # 生成高清像素矩阵
        # ==========================================================

        for page in doc:
            # 关键：使用 matrix 生成高清图片
            pix = page.get_pixmap(matrix=matrix)
            img = Image.frombytes("RGB", [pix.width, pix.height], pix.samples)
            img_list.append(img)

        if not img_list:
            print("错误：PDF中没有页面！")
            return

        total_width = img_list[0].width
        total_height = sum(img.height for img in img_list)

        long_image = Image.new("RGB", (total_width, total_height), "white")
        current_y = 0
        for img in img_list:
            long_image.paste(img, (0, current_y))
            current_y += img.height

        # 保存为最高质量 PNG
        long_image.save(output_path, format="PNG", quality=100)
        print(f"✅ 高清长图保存完成：{os.path.abspath(output_path)}")

    except Exception as e:
        print(f"❌ 处理失败：{str(e)}")
    finally:
        if 'doc' in locals():
            doc.close()

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("使用方法：")
        print(f"  python {sys.argv[0]} 你的文件.pdf")
        print(f"  python {sys.argv[0]} 你的文件.pdf 输出长图.png")
        sys.exit(1)

    pdf_file = sys.argv[1]
    output_file = sys.argv[2] if len(sys.argv) >= 3 else "长图.png"
    pdf_to_long_image(pdf_file, output_file)